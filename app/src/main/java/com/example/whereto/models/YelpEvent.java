package com.example.whereto.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YelpEvent {
    @SerializedName("name")
    private String name;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("category")
    private String category;
    @SerializedName("cost")
    private double cost;
    @SerializedName("is_free")
    private boolean is_free;
    @SerializedName("time_start")
    private String time_start;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCost() {
        return cost;
    }

    public boolean isFree() {
        return is_free;
    }

    public String getTimeStart() {
        return time_start;
    }
}
