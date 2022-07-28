package com.example.whereto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereto.Constants;
import com.example.whereto.LoginActivity;
import com.example.whereto.R;
import com.example.whereto.models.BusinessAdapter;
import com.example.whereto.models.Itinerary;
import com.example.whereto.models.SharedViewModel;
import com.example.whereto.models.UserPreferences;
import com.example.whereto.models.YelpBusiness;
import com.example.whereto.models.YelpCategory;
import com.example.whereto.models.YelpEvent;
import com.example.whereto.models.YelpService;
import com.example.whereto.models.YelpServiceInterface;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    List<YelpBusiness> matchedBusinesses = new ArrayList<>();
    List<YelpEvent> matchedEvents = new ArrayList<>();
    UserPreferences userPreferences;
    HashMap<String, List<YelpCategory>> allBusinessCategories = new HashMap<>();
    private SharedViewModel model;
    BusinessAdapter adapter;
    RecyclerView rvItinerary;

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.item.observe(getActivity(), new Observer<UserPreferences>() {

            @Override
            public void onChanged(@Nullable UserPreferences updatedObject) {
                if (updatedObject != null) userPreferences = updatedObject;
            }
        });
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readFromJsonFile();
        getYelpData();

        adapter = new BusinessAdapter(getContext(), matchedBusinesses);
        rvItinerary = view.findViewById(R.id.rvItinerary);
        rvItinerary.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItinerary.setAdapter(adapter);

        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void getYelpData() {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final YelpServiceInterface yelpServiceInterface = retrofit.create(YelpServiceInterface.class);
        getSearchResults(yelpServiceInterface);
    }

    public void getSearchResults(YelpServiceInterface yelpServiceInterface) {
        String location = userPreferences.getDestination();
        getBusinessResults(yelpServiceInterface, location);
        getEventsResults(yelpServiceInterface, location);
    }

    public void getBusinessResults(final YelpServiceInterface yelpServiceInterface, final String location) {
        Call<YelpService> restaurantCall = yelpServiceInterface.searchRestaurants("Bearer " + Constants.API_KEY, location);
        restaurantCall.enqueue(new Callback<YelpService>() {
            @Override
            public void onResponse(Call<YelpService> call, Response<YelpService> response) {
                if (response.body() != null) {
                    for (YelpBusiness business : response.body().getBusinesses()) {
                        if (userPreferences.keepSearching()) break;
                        try {
                            if (userPreferences.matchBusinessPreferences(business, allBusinessCategories) && userPreferences.appropriateDistance(business)) {
                                matchedBusinesses.add(business);
                                ParseUser currentUser = ParseUser.getCurrentUser();
                                saveItinerary(currentUser, business);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                adapter.addAll(matchedBusinesses);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<YelpService> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getEventsResults(final YelpServiceInterface yelpServiceInterface, final String location) {
        Call<YelpService> eventsCall = yelpServiceInterface.searchEvents("Bearer " + Constants.API_KEY, location);
        eventsCall.enqueue(new Callback<YelpService>() {
            @Override
            public void onResponse(Call<YelpService> eventsCall, Response<YelpService> response) {
                if (response.body() != null) {
                    for (YelpEvent event : response.body().getEvents()) {
                        try {
                            if (userPreferences.matchEventPreferences(event))
                                matchedEvents.add(event);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<YelpService> eventsCall, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void saveItinerary(ParseUser currentUser, YelpBusiness business) {
        Itinerary itinerary = new Itinerary();
        itinerary.setName(business.getName());
        itinerary.setImage(new ParseFile(new File(business.getImageUrl())));
        itinerary.setRating(business.getRating());

        String address = "";
        if (business.getAddress() != null) {
            for (String addressLine : business.getAddress()) {
                address += addressLine;
            }
        }
        itinerary.setAddress(address);
        itinerary.setDistance(business.getDistanceAway());

        String categories = "";
        for (YelpCategory category : business.getCategories()) {
            categories += category.getTitle() + ", ";
        }
        itinerary.setCategories(categories);
        itinerary.setUser(currentUser);

        itinerary.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getContext(), "Error posting", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean mapContains(String title) {
        for (List<YelpCategory> list : allBusinessCategories.values()) {
            for (YelpCategory category : list) {
                if (category.getTitle().equals(title)) return true;
            }
        }
        return false;
    }

    private String getKeyFromValue(String value) {
        for (String key : allBusinessCategories.keySet()) {
            for (YelpCategory category : allBusinessCategories.get(key)) {
                if (category.getTitle().equals(value)) return key;
            }
        }
        return null;
    }

    public void readFromJsonFile() {
        try {
            // get the contents of categories.json as a string
            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier("categories",
                            "raw", getContext().getPackageName()));
            String jsonFileInput = convertStreamToString(ins);

            JSONObject obj = new JSONObject(jsonFileInput);
            JSONArray arr = obj.getJSONArray("categories");

            //iterate through each category
            for (int i = 0; i < arr.length(); ++i) {
                JSONObject currObject = arr.getJSONObject(i);

                // each category has an 'alias' (it's name), and an array of 'parents' (the larger category this sub-category belongs to -- for example, Italian food's parents would be food)
                String categoryName = currObject.getString("alias");
                JSONArray parentCategories = currObject.getJSONArray("parents");

                // iterate through each parent
                for (int j = 0; j < parentCategories.length(); ++j) {
                    String parentCategory = (String) parentCategories.get(j);

                    YelpCategory newCategory = new YelpCategory();
                    newCategory.setTitle(categoryName);

                    // if parentCategory is already in the hashmap as a key, add categoryName in as a child of the parent
                    if (allBusinessCategories.containsKey(parentCategory)) {
                        allBusinessCategories.get(parentCategory).add(newCategory);
                    }
                    // if parentCategory is already in the hashmap as a value, add categoryName in as a child of the parent's parent
                    // this is because only the 'top most' category is needed when filtering
                    else if (mapContains(parentCategory)) {
                        String grandparentCategory = getKeyFromValue(parentCategory);
                        allBusinessCategories.get(grandparentCategory).add(newCategory);
                    }
                    // if parentCategory is not in the hashmap
                    else {
                        // check to see if categoryName was already added by one of its sub-categories (as that sub-category's parentCategory)
                        if (allBusinessCategories.containsKey(categoryName)) {
                            // get all the sub-categories that fall under the current category
                            List<YelpCategory> childrenList = allBusinessCategories.get(categoryName);
                            // add the current category to that list too
                            childrenList.add(newCategory);
                            // add that list back to the hashmap, but as sub-categories of the current category's parentCategory
                            allBusinessCategories.put(parentCategory, childrenList);
                            // remove the original list from the hashmap
                            allBusinessCategories.remove(categoryName);
                        } else {
                            // otherwise, just add the current category into the hashmap as a sub-category of its parentCategory
                            List<YelpCategory> childrenList = new ArrayList<>();
                            childrenList.add(newCategory);
                            allBusinessCategories.put(parentCategory, childrenList);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}