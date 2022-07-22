package com.example.whereto.models;

import com.google.gson.annotations.SerializedName;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Itinerary")
public class Itinerary extends ParseObject {
    public static final String KEY_NAME = "attractionName";
    public static final String KEY_IMAGE = "attractionImage";
    public static final String KEY_USER = "user";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_RATING = "rating";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_CATEGORIES = "categories";

    private String name;
    private String imageUrl;
    private double rating;
    private double distanceAway;
    private String[] display_address;
    private List<YelpCategory> categories;
    private YelpLocation location;

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getAddress() {
        return getString(KEY_ADDRESS);
    }

    public void setAddress(String address) {
        put(KEY_ADDRESS, address);
    }

    public double getRating() {
        return getDouble(KEY_RATING);
    }

    public void setRating(double rating) {
        put(KEY_RATING, rating);
    }

    public double getDistance() {
        return getDouble(KEY_DISTANCE);
    }

    public void setDistance(double distance) {
        put(KEY_DISTANCE, distance);
    }

    public String getCategories() {
        return getString(KEY_CATEGORIES);
    }

    public void setCategories(String categories) {
        put(KEY_CATEGORIES, categories);
    }


    public String getBusinessName() {
        return name;
    }

    public void setBusinessName(String name) {
        this.name = name;
    }

    public String getBusinessImageUrl() {
        return imageUrl;
    }

    public void setBusinessImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getBusinessRating() {
        return rating;
    }

    public void setBusinessRating(double rating) {
        this.rating = rating;
    }

    public double getBusinessDistanceAway() {
        return distanceAway*0.000621371;
    }

    public void setBusinessDistanceAway(double distanceAway) {
        this.distanceAway = distanceAway*0.000621371;
    }

    public List<YelpCategory> getBusinessCategories() {
        return categories;
    }

    public String[] getBusinessAddress() {
        return display_address;
    }
}
