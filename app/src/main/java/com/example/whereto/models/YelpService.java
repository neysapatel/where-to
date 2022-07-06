package com.example.whereto.models;

import com.example.whereto.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class YelpService implements YelpServiceInterface{

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

     @Override
     public Call<YelpService> searchRestaurants(String authHeader, String searchTerm, String location) {
         return null;
     }

     @Override
     public Call<YelpService> searchEvents(String authHeader, String searchTerm, String location) {
         return null;
     }
 }
