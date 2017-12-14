package com.example.vaevictis.myapplication.models;

/**
 * Created by vaevictis on 01.12.17.
 */

public class RawLocation {

    private double[] coordinates;

    private String type = "Point";

    public RawLocation(double latitude, double longitude) {
        this.coordinates = new double[2];
        this.coordinates[0] = longitude;
        this.coordinates[1] = latitude;
    }

    public double getLatitude() {
        return coordinates[1];
    }

    public double getLongitude() {
        return coordinates[0];
    }
}
