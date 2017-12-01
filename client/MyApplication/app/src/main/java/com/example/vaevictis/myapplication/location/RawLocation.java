package com.example.vaevictis.myapplication.location;

/**
 * Created by vaevictis on 01.12.17.
 */

public class RawLocation {

    private double latitude;
    private double longitude;

    public RawLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
