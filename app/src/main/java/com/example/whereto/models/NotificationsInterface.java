package com.example.whereto.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NotificationsInterface {
    @GET("current.json")
    Call<WeatherService> getWeather(@Header("key") String key, @Query("q") String city);
}
