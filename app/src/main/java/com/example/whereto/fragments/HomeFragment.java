package com.example.whereto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.whereto.Constants;
import com.example.whereto.R;
import com.example.whereto.models.UserPreferences;
import com.example.whereto.models.YelpBusiness;
import com.example.whereto.models.YelpCategory;
import com.example.whereto.models.YelpService;
import com.example.whereto.models.YelpServiceInterface;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    List<YelpBusiness> matchedBusinesses = new ArrayList<>();
    UserPreferences userPreferences;
    HashMap<String, List<YelpCategory>> allCategories = new HashMap<String, List<YelpCategory>>();

    final String FOOD = "food";
    final String RESTAURANTS = "restaurants";
    final String HOTELS = "hotels";
    final String TOURS = "tours";
    final String ACTIVE = "active";
    final String ARTS = "arts";
    final String OUTLET_STORES = "outlet_stores";
    final String SHOPPING_CENTRES = "shoppingcenters";
    final String SOUVENIRS = "souvenirs";
    final String BARS = "bars";
    final String SPAS = "beautysvc";

    public HomeFragment() {
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = new Intent();
        userPreferences = Parcels.unwrap(intent.getParcelableExtra("preferences"));
        readFromJsonFile();
        getYelpData();
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
        int radius = userPreferences.getRadius();
        String searchCategory = "Concert"; // TODO: replace with user input
        String location = userPreferences.getDestination(); // TODO: replace with user input

        getRestaurantResults(yelpServiceInterface, radius, location);
        getEventsResults(yelpServiceInterface, searchCategory, location);
    }

    public void getRestaurantResults(final YelpServiceInterface yelpServiceInterface, final int radius, final String location) {
        Call<YelpService> restaurantCall = yelpServiceInterface.searchRestaurants("Bearer " + Constants.API_KEY, radius, location);
        restaurantCall.enqueue(new Callback<YelpService>() {
            @Override
            public void onResponse(Call<YelpService> call, Response<YelpService> response) {
                // TODO: store and return response based on the user's preferences
                if (response.body() != null) {
                    for (YelpBusiness b : response.body().getBusinesses()) {
                        boolean matched = matchPreferences(b);
                        if (matched) {
                            matchedBusinesses.add(b);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<YelpService> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getEventsResults(final YelpServiceInterface yelpServiceInterface, final String searchCategory, final String location) {
        Call<YelpService> eventsCall = yelpServiceInterface.searchEvents("Bearer " + Constants.API_KEY, searchCategory, location);
        eventsCall.enqueue(new Callback<YelpService>() {
            @Override
            public void onResponse(Call<YelpService> eventsCall, Response<YelpService> response) {
                // TODO: store and return response based on the user's preferences
            }

            @Override
            public void onFailure(Call<YelpService> eventsCall, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public boolean matchPreferences(YelpBusiness business) {
        List<YelpCategory> categories = business.getCategories();
        for (YelpCategory c : categories) {
            String title = c.getTitle();
            String parent = "";

            if (mapContains(title)) {
                parent = getKeyFromValue(title);
            }

            if (UserPreferences.isFood()) {
                if (title.equals(FOOD) || title.equals(RESTAURANTS) || parent.equals(FOOD) || parent.equals(RESTAURANTS)) {
                    return true;
                }
            } else if (UserPreferences.isHotels()) {
                if (title == HOTELS || parent == HOTELS) {
                    return true;
                }
            } else if (UserPreferences.isTours()) {
                if (title == TOURS || parent == TOURS) {
                    return true;
                }
            } else if (UserPreferences.isAthletic()) {
                if (title == ACTIVE || parent == ACTIVE) {
                    return true;
                }
            } else if (UserPreferences.isArts()) {
                if (title == ARTS || parent == ARTS) {
                    return true;
                }
            } else if (UserPreferences.isShopping()) {
                if (title == OUTLET_STORES || title == SHOPPING_CENTRES || title == SOUVENIRS || parent == OUTLET_STORES || parent == SHOPPING_CENTRES || parent == SOUVENIRS) {
                    return true;
                }
            } else if (UserPreferences.isBars()) {
                if (title == BARS || parent == BARS) {
                    return true;
                }
            } else if (UserPreferences.isBeauty()) {
                if (title == SPAS || parent == SPAS) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean mapContains(String title) {
        for (List<YelpCategory> list : allCategories.values()) {
            for (YelpCategory c : list) {
                if (c.getTitle().equals(title)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getKeyFromValue(String value) {
        for (String key : allCategories.keySet()) {
            for (YelpCategory c : allCategories.get(key)) {
                if (c.getTitle().equals(value)) {
                    return key;
                }
            }
        }
        return null;
    }

    public ArrayList<YelpCategory> readFromJsonFile() {
        ArrayList<YelpCategory> result = new ArrayList<YelpCategory>();

        try {
            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier("categories",
                            "raw", getContext().getPackageName()));
            String jsonFileInput = convertStreamToString(ins);

            JSONObject obj = new JSONObject(jsonFileInput);
            JSONArray arr = obj.getJSONArray("categories");

            for (int i = 0; i < arr.length(); ++i) {
                JSONObject currObject = arr.getJSONObject(i);
                String title = currObject.getString("alias");

                JSONArray parents = currObject.getJSONArray("parents");
                for (int j = 0; j < parents.length(); ++j) {
                    String parent = (String) parents.get(j);

                    if (parent != null) {
                        YelpCategory newCategory = new YelpCategory();
                        newCategory.setTitle(title);

                        // if the parent category exists as a key, add the title in as a child of the parent
                        if (allCategories.containsKey(parent)) {
                            allCategories.get(parent).add(newCategory);
                        }
                        // if the parent exists as a value, add the title in as a child of the parent's parent
                        else if (mapContains(parent)) {
                            String grandparent = getKeyFromValue(parent);
                            allCategories.get(grandparent).add(newCategory);
                        } else {
                            // check to see if this key was already added by another child
                            if (allCategories.containsKey(title)) {
                                List<YelpCategory> childrenList = allCategories.get(title);
                                childrenList.add(newCategory);

                                allCategories.put(parent, childrenList);
                                allCategories.remove(title);
                            } else {
                                List<YelpCategory> childrenList = new ArrayList<>();
                                childrenList.add(newCategory);

                                allCategories.put(parent, childrenList);
                            }
                        }
                    }
                    // if there is no parent, add title in to hashmap
                    else {
                        allCategories.put(title, null);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return result;
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