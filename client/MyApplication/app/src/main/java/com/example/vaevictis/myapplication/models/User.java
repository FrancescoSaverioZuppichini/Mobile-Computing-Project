package com.example.vaevictis.myapplication.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by vaevictis on 15.11.17.
 */

public class User {
    private String email;
    private String password;
    private String role = "USER";
    private String _id;
    private Token token;
    private double dist;
    public Marker marker = null;
    private String avatar;
    private RawLocation location;


    public User(){
    }

    public void setEmailAndPassword(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public void setMarker(Marker marker){
        this.marker = marker;
    }

    public Marker getMarker(){
        return marker;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String get_id() {
        return _id;
    }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public RawLocation getLocation() {
        return location;
    }

    public void setLocation(double latitude, double longitude) {
        this.location = new RawLocation(latitude, longitude);
    }

    public LatLng getLatLng() {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }


}

