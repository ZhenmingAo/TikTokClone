package com.example.tiktokclone;


import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject implements Parcelable{

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_VIDEO = "video";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getVideo(){
        return getParseFile(KEY_VIDEO);
    }

    public void setVideo(ParseFile parseFile){
        put(KEY_VIDEO, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }


}
