package com.example.vaevictis.myapplication.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by vaevictis on 01.12.17.
 */
public class LocationController implements LocationListener{

    private LocationManager locationManager;
    private Context context;

    private int MIN_TIME = 3000;
    private float MIN_DISTANCE = (float) 0.1;

    public LocationController(Context context) {

        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void initialise(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            this.onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

            ActivityCompat.requestPermissions((Activity) context,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location == null) return;

        UserController.user.setLocation(location.getLatitude(), location.getLongitude());

        UserController userController = new UserController(this.context);

        userController.updateUser();
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}