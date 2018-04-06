package com.ozenix.achieveme;

import java.util.ArrayList;

/**
 * Created by c16024036 on 03/04/18.
 */

public class Profile {
    private String userId;
    private String username;
    private ArrayList<Achievement> achievement;

    public Profile(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }
}
