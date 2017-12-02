package com.example.vaevictis.myapplication.user;

import com.example.vaevictis.myapplication.Token;
import com.example.vaevictis.myapplication.location.RawLocation;

/**
 * Created by vaevictis on 15.11.17.
 */

public class User {
    private String email;
    private String password;
    private String role = "USER";
    private Token token;
    private RawLocation location;

    public User(){

    }
    public void setEmailAndPassword(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
