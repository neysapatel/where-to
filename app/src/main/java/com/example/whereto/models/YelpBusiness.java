package com.example.whereto.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YelpBusiness {
    @SerializedName("name")
    private String name;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("rating")
    private double rating;
    @SerializedName("distance")
    private double distanceAway;
   // @SerializedName("display_address");
    private String[] display_address;
    private List<YelpCategory> categories;
    private YelpLocation location;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getDistanceAway() {
        return distanceAway*0.000621371;
    }

    public void setDistanceAway(double distanceAway) {
        this.distanceAway = distanceAway*0.000621371;
    }

    public List<YelpCategory> getCategories() {
        return categories;
    }

    public String[] getAddress() {
        return display_address;
    }
}
