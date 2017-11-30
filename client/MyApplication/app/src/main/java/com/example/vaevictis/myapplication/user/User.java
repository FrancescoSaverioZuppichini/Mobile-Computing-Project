package com.example.vaevictis.myapplication.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vaevictis on 15.11.17.
 */

public class User {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }
}
