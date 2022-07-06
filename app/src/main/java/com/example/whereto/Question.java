package com.example.whereto;

public class Question {
    private String attractionName;
    private String attractionLocation;
    private String attractionDescription;

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String name) {
        this.attractionName = name;
    }

    public String getAttractionLocation() {
        return attractionLocation;
    }

    public void setAttractionLocation(String location) {
        this.attractionLocation = location;
    }


    public String getAttractionDescription() {
        return attractionDescription;
    }

    public void setAttractionDescription(String description) {
        this.attractionDescription = description;
    }

    public Question(String name, String location, String description) {
        this.attractionName = name;
        this.attractionLocation = location;
        this.attractionDescription = description;
    }
}
