package com.example.vaevictis.myapplication.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vaevictis on 15.11.17.
 */

public class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }
}
