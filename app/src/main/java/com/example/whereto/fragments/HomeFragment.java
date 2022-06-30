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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        YelpService yelpService = retrofit.create(YelpService.class);

        String searchTerm = "Avocado Toast"; // TODO: replace with user input
        String searchCategory = "Concert"; // TODO: replace with user input
        String location = "San Francisco"; // TODO: replace with user input

        Call<YelpFusion> restaturantCall = yelpService.searchRestaurants("Bearer "+ Constants.API_KEY, searchTerm, location);
        restaturantCall.enqueue(new Callback<YelpFusion>() {
            @Override
            public void onResponse(Call<YelpFusion> call, Response<YelpFusion> response) {
                // TODO: store and return response based on the user's preferences
            }

            @Override
            public void onFailure(Call<YelpFusion> call, Throwable t) {
                t.printStackTrace();
            }
        });

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}