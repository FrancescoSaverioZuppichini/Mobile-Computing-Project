package com.example.vaevictis.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MyMapFragment extends android.app.Fragment implements OnMapReadyCallback {
    private View myView;
    private GoogleMap googleMapInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.maps, container, false);

        return myView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("WEEEE");
    }
}
