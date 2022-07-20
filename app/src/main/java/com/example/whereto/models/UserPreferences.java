package com.example.whereto.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserPreferences {
    private String destination;
    private float budget;
    private float radius;
    private String tripStart;
    private String tripEnd;
    private boolean food = false;
    private boolean hotels = false;
    private boolean tours = false;
    private boolean athletic = false;
    private boolean arts = false;
    private boolean shopping = false;
    private boolean bars = false;
    private boolean beauty = false;

    final String FOOD = "food";
    final String RESTAURANTS = "restaurants";
    final String HOTELS = "hotels";
    final String TOURS = "tours";
    final String ACTIVE = "active";
    final String ARTS = "arts";
    final String OUTLET_STORES = "outlet_stores";
    final String SHOPPING_CENTRES = "shoppingcenters";
    final String SOUVENIRS = "souvenirs";
    final String BARS = "bars";
    final String SPAS = "beautysvc";
    final String MUSIC = "music";
    final String VISUAL_ARTS = "visual-arts";
    final String PERFORMING_ARTS = "performing-arts";
    final String FILM = "film";
    final String LECTURES_BOOK = "lectures-book";
    final String FASHION = "fashion";
    final String FOOD_DRINK = "food-and-drink";
    final String FESTIVALS_FAIRS = "festivals-fairs";
    final String CHARITIES = "charities";
    final String SPORTS = "sports-active-life";
    final String NIGHTLIFE = "nightlife";
    final String FAMILY = "kids-family";
    final String OTHER = "other";

    final List<String> allEventCategories = Arrays.asList(MUSIC, VISUAL_ARTS, PERFORMING_ARTS, FILM, LECTURES_BOOK, FASHION, FOOD_DRINK, FESTIVALS_FAIRS, CHARITIES, SPORTS, NIGHTLIFE, FAMILY, OTHER);

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
        return Math.round(radius);
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

    public boolean isBars() {
        return bars;
    }

    public void setBars(boolean bars) {
        this.bars = bars;
    }

    public boolean isBeauty() {
        return beauty;
    }

    public void setBeauty(boolean beauty) {
        this.beauty = beauty;
    }


    public boolean matchBusinessPreferences(YelpBusiness business, HashMap<String, List<YelpCategory>> allBusinessCategories) {
        List<YelpCategory> categories = business.getCategories();
        for (YelpCategory category : categories) {
            String categoryName = category.getTitle();
            String categoryParent = "";

            if (mapContains(categoryName, allBusinessCategories))
                categoryParent = getKeyFromValue(categoryName, allBusinessCategories);

            if (isFood() && (categoryName.equals(FOOD) || categoryName.equals(RESTAURANTS) || categoryParent.equals(FOOD) || categoryParent.equals(RESTAURANTS))) return true;
            else if (isHotels() && (categoryName == HOTELS || categoryParent == HOTELS)) return true;
            else if (isTours() && (categoryName == TOURS || categoryParent == TOURS)) return true;
            else if (isAthletic() && (categoryName == ACTIVE || categoryParent == ACTIVE)) return true;
            else if (isArts() && (categoryName == ARTS || categoryParent == ARTS)) return true;
            else if (isShopping() && (categoryName == OUTLET_STORES || categoryName == SHOPPING_CENTRES || categoryName == SOUVENIRS || categoryParent == OUTLET_STORES || categoryParent == SHOPPING_CENTRES || categoryParent == SOUVENIRS)) return true;
            else if (isBars() && (categoryName == BARS || categoryParent == BARS)) return true;
            else if (isBeauty() && (categoryName == SPAS || categoryParent == SPAS)) return true;
        }
        return false;
    }

    private boolean mapContains(String title, HashMap<String, List<YelpCategory>> allBusinessCategories) {
        for (List<YelpCategory> list : allBusinessCategories.values()) {
            for (YelpCategory category : list) {
                if (category.getTitle().equals(title)) return true;
            }
        }
        return false;
    }

    private String getKeyFromValue(String value, HashMap<String, List<YelpCategory>> allBusinessCategories) {
        for (String key : allBusinessCategories.keySet()) {
            for (YelpCategory category : allBusinessCategories.get(key)) {
                if (category.getTitle().equals(value)) return key;
            }
        }
        return null;
    }

    public boolean appropriateDistance(YelpBusiness business) {
        return (business.getDistanceAway() <= radius);
    }

    public boolean matchEventPreferences(YelpEvent event) {
        return allEventCategories.contains(event.getCategory());
    }
}
