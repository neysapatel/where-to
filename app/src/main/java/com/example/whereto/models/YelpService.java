package com.example.whereto.models;

import com.example.whereto.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class YelpService {
    @SerializedName("total")
    private int total;
    @SerializedName("businesses")
    private List<YelpBusiness> businesses;
     @SerializedName("events")
     private List<YelpEvent> events;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<YelpBusiness> getBusinesses() {
        return businesses;
    }

     public List<YelpEvent> getEvents() {
         return events;
     }

    public void setBusinesses(List<YelpBusiness> businesses) {
        this.businesses = businesses;
    }
 }
