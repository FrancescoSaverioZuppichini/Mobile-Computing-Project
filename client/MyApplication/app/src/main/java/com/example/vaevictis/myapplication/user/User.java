package com.example.vaevictis.myapplication.user;

/**
 * Created by vaevictis on 15.11.17.
 */

public class User {
    private String email;
    private String password;
    String token;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
