package com.example.whereto.models;

import android.view.View;

import org.parceler.Parcel;

@Parcel
public class UserPreferences {
    private String destination;
    private float budget = 0;
    private int radius = 0;
    private String tripStart;
    private String tripEnd;
    private static boolean food = false;
    private static boolean hotels = false;
    private static boolean tours = false;
    private static boolean athletic = false;
    private static boolean arts = false;
    private static boolean shopping = false;
    private static boolean bars = false;
    private static boolean beauty = false;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
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

    public static boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public static boolean isHotels() {
        return hotels;
    }

    public void setHotels(boolean hotels) {
        this.hotels = hotels;
    }

    public static boolean isTours() {
        return tours;
    }

    public void setTours(boolean tours) {
        this.tours = tours;
    }

    public static boolean isAthletic() {
        return athletic;
    }

    public void setAthletic(boolean athletic) {
        this.athletic = athletic;
    }

    public static boolean isArts() {
        return arts;
    }

    public void setArts(boolean arts) {
        this.arts = arts;
    }

    public static boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    public static boolean isBars() {
        return bars;
    }

    public void setBars(boolean bars) {
        this.bars = bars;
    }

    public static boolean isBeauty() {
        return beauty;
    }

    public void setBeauty(boolean beauty) {
        this.beauty = beauty;
    }
}
