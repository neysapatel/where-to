package com.example.whereto;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("P0cLKI8pJY02WAIHqUd8K0Z73rEp4UMZFcgQ4wvo")
                .clientKey("UuZ6LchYysOaeKPQWR0UMqQTGEKEErj3JC0fSjZW")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
