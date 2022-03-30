package com.example.tiktokclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Register parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("pmAu3yLK2oWYfl4VMsIBqMaOQUfOvDStWEMMY2WP")
                .clientKey("2UbH6tLKb9wLQhixYVIL0lVGaiu0xou4aYwjhYlw")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
