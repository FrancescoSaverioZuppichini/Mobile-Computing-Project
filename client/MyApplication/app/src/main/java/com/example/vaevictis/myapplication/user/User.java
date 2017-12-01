package com.example.vaevictis.myapplication.user;

import com.example.vaevictis.myapplication.Token;
import com.example.vaevictis.myapplication.location.RawLocation;

/**
 * Created by vaevictis on 15.11.17.
 */

public class User {
    private String email;
    private String password;
    private Token token;
    private RawLocation location;


    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public Token getToken() {
        return token;
    }

    public RawLocation getLocation() {
        return location;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setLocation(double latitude, double longitude) {
        this.location = new RawLocation(latitude, longitude);
    }
}
