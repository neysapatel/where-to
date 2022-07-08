package com.example.whereto.models;

public class UserPreferences {
    private float budget = 0;
    private float radius = 0;
    private String tripStart;
    private String tripEnd;
    private boolean food = false;
    private boolean hotels = false;
    private boolean tours = false;
    private boolean athletic = false;
    private boolean arts = false;
    private boolean shopping = false;
    private boolean nightlife = false;
    private boolean beauty = false;

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getTripStart() {
        return tripStart;
    }

    public void setTripStart(String tripStart) {
        this.tripStart = tripStart;
    }

    public String getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(String tripEnd) {
        this.tripEnd = tripEnd;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isHotels() {
        return hotels;
    }

    public void setHotels(boolean hotels) {
        this.hotels = hotels;
    }

    public boolean isTours() {
        return tours;
    }

    public void setTours(boolean tours) {
        this.tours = tours;
    }

    public boolean isAthletic() {
        return athletic;
    }

    public void setAthletic(boolean athletic) {
        this.athletic = athletic;
    }

    public boolean isArts() {
        return arts;
    }

    public void setArts(boolean arts) {
        this.arts = arts;
    }

    public boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    public boolean isNightlife() {
        return nightlife;
    }

    public void setNightlife(boolean nightlife) {
        this.nightlife = nightlife;
    }

    public boolean isBeauty() {
        return beauty;
    }

    public void setBeauty(boolean beauty) {
        this.beauty = beauty;
    }
}
