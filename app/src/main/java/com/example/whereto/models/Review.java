package com.example.whereto.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Review")
public class Review extends ParseObject {

    public static final String KEY_USER_REVIEW = "user_review";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";

    public String getKeyUserReview() {
        return getString(KEY_USER_REVIEW);
    }

    public void setKeyUserReview(String userReview) {
        put(KEY_USER_REVIEW, userReview);
    }

    public String getKeyUser() {
        return getString(getKeyUser());
    }

    public void setKeyUser(ParseUser user) {
        put(KEY_USER, user);
    }
}
