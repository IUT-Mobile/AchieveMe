package com.ozenix.achieveme;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Profile {
    private String userId;
    private String email;
    private String username;
    private Uri imageUrl;

    private ArrayList<Achievement> userAchievements;

    public Profile(String userId, String email, String username, Uri imageUrl) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Uri getImageUrl() {
        return imageUrl;
    }

    public void addAchievement(Achievement achievement) {
        userAchievements.add(achievement);
    }
}
