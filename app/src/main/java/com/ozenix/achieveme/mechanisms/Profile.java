package com.ozenix.achieveme.mechanisms;

import android.net.Uri;

import com.ozenix.achieveme.mechanisms.Achievement;

import java.util.ArrayList;


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
