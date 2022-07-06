package com.example.whereto.models;

import com.example.whereto.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public abstract class YelpService implements YelpServiceInterface{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("properties")
    @Expose
    private Properties properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void getSearchResults() {
        String searchTerm = "Avocado Toast"; // TODO: replace with user input
        String searchCategory = "Concert"; // TODO: replace with user input
        String location = "San Francisco"; // TODO: replace with user input

        getRestaurantResults(searchTerm, location);
        getEventsResults(searchCategory, location);
    }

    public void getRestaurantResults(final String searchTerm, final String location) {
        Call<YelpService> restaurantCall = searchRestaurants("Bearer "+ Constants.API_KEY, searchTerm, location);
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

    public void getEventsResults(final String searchCategory, final String location) {
        Call<YelpService> eventsCall = searchEvents("Bearer "+ Constants.API_KEY, searchCategory, location);
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
