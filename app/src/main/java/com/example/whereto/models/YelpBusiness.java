package com.example.whereto.models;

import com.google.gson.annotations.SerializedName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

// extends ParseObject
public class YelpBusiness {
    @SerializedName("name")
    private String name;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("rating")
    private double rating;
    @SerializedName("distance")
    private double distanceAway;
    private String[] display_address;
    private List<YelpCategory> categories;
    private YelpLocation location;

    /*public static final String KEY_NAME = "attractionName";
    public static final String KEY_IMAGE = "attractionImage";
    public static final String KEY_USER = "user";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_RATING = "rating";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_CATEGORIES = "categories";

    public YelpBusiness() {
    }

     */

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

    /*
    public String getParseName() {
        return getString(KEY_NAME);
    }

    public void setParseName(String name) {
        put(KEY_NAME, name);
    }

    public ParseFile getParseImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setParseImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getParseUser() {
        return getParseUser(KEY_USER);
    }

    public void setParseUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getParseAddress() {
        return getString(KEY_ADDRESS);
    }

    public void setParseAddress(String address) {
        put(KEY_ADDRESS, address);
    }

    public double getParseRating() {
        return getDouble(KEY_RATING);
    }

    public void setParseRating(double rating) {
        put(KEY_RATING, rating);
    }

    public double getParseDistance() {
        return getDouble(KEY_DISTANCE);
    }

    public void setParseDistance(double distance) {
        put(KEY_DISTANCE, distance);
    }

    public String getParseCategories() {
        return getString(KEY_CATEGORIES);
    }

    public void setParseCategories(String categories) {
        put(KEY_CATEGORIES, categories);
    }

     */
}
