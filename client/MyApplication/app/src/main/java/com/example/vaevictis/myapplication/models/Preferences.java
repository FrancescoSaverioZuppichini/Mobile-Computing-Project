package com.example.vaevictis.myapplication.models;

/**
 * Created by vaevictis on 18.12.17.
 */

public class Preferences {

    int radiusInMeters;

    public Preferences(int radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public int getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(int radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }
}
