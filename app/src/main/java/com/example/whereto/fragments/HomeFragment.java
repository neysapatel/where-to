package com.example.whereto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whereto.Constants;
import com.example.whereto.Question;
import com.example.whereto.QuestionsAdapter;
import com.example.whereto.R;
import com.example.whereto.models.YelpService;
import com.example.whereto.models.YelpServiceInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    public HomeFragment() {
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        String searchTerm = "Avocado Toast"; // TODO: replace with user input
        String searchCategory = "Concert"; // TODO: replace with user input
        String location = "San Francisco"; // TODO: replace with user input

        getRestaurantResults(yelpServiceInterface, searchTerm, location);
        getEventsResults(yelpServiceInterface, searchCategory, location);
    }

    public void getRestaurantResults(final YelpServiceInterface yelpServiceInterface, final String searchTerm, final String location) {
        Call<YelpService> restaurantCall = yelpServiceInterface.searchRestaurants("Bearer "+ Constants.API_KEY, searchTerm, location);
        restaurantCall.enqueue(new Callback<YelpService>() {
            @Override
            public void onResponse(Call<YelpService> call, Response<YelpService> response) {
                // TODO: store and return response based on the user's preferences
            }

            @Override
            public void onFailure(Call<YelpService> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getEventsResults(final YelpServiceInterface yelpServiceInterface, final String searchCategory, final String location) {
        Call<YelpService> eventsCall = yelpServiceInterface.searchEvents("Bearer "+ Constants.API_KEY, searchCategory, location);
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
}