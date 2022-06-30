package com.example.whereto.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YelpService {
        @GET("businesses/search")
        Call<YelpFusion> searchRestaurants(@Header("Authorization") String authHeader, @Query("term") String searchTerm, @Query("location") String location);
        Call<YelpFusion> searchEvents(@Header("Authorization") String authHeader, @Query("category") String searchTerm, @Query("location") String location);
    }
