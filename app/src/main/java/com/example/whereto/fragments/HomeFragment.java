package com.example.whereto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whereto.Constants;
import com.example.whereto.R;
import com.example.whereto.models.YelpFusion;
import com.example.whereto.models.YelpService;

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
        final YelpService yelpService = retrofit.create(YelpService.class);

        String searchTerm = "Avocado Toast"; // TODO: replace with user input
        String searchCategory = "Concert"; // TODO: replace with user input
        String location = "San Francisco"; // TODO: replace with user input

        getRestaurantResults(yelpService, searchTerm, location);
        getEventsResults(yelpService, searchCategory, location);
    }

    public void getRestaurantResults(final YelpService yelpService, final String searchTerm, final String location) {
        Call<YelpFusion> restaurantCall = yelpService.searchRestaurants("Bearer "+ Constants.API_KEY, searchTerm, location);
        restaurantCall.enqueue(new Callback<YelpFusion>() {
            @Override
            public void onResponse(Call<YelpFusion> call, Response<YelpFusion> response) {
                // TODO: store and return response based on the user's preferences
            }

            @Override
            public void onFailure(Call<YelpFusion> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getEventsResults(final YelpService yelpService, final String searchCategory, final String location) {
        Call<YelpFusion> eventsCall = yelpService.searchEvents("Bearer "+ Constants.API_KEY, searchCategory, location);
        eventsCall.enqueue(new Callback<YelpFusion>() {
            @Override
            public void onResponse(Call<YelpFusion> eventsCall, Response<YelpFusion> response) {
                // TODO: store and return response based on the user's preferences
            }

            @Override
            public void onFailure(Call<YelpFusion> eventsCall, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}