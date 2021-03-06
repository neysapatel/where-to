package com.example.whereto.models;

import com.example.whereto.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YelpServiceInterface {
        @GET("businesses/search")
        Call<YelpService> searchRestaurants(@Header("Authorization") String authHeader, @Query("location") String location);

        @GET("events")
        Call<YelpService> searchEvents(@Header("Authorization") String authHeader, @Query("location") String location);
}
