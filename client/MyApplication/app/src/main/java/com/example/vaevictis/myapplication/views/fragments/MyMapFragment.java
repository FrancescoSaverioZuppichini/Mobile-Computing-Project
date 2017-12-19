package com.example.vaevictis.myapplication.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.controllers.UserController;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {
    private View myView;
    public SupportMapFragment mapFrag;
    static public GoogleMap map = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.maps, container, false);

        mapFrag = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapsfragment);
        mapFrag.getMapAsync(this);

        return myView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.clear();
        System.out.println("MAP READY");
        UserController.currentMap = map;
        Circle circle = map.addCircle(new CircleOptions()
                .center(UserController.user.getLatLng())
                .radius(UserController.user.getPreferences().getRadiusInMeters())
                .strokeColor(Color.RED)
                .fillColor(0x40ff0000));

        (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new UserController(getContext()).updateMap();            }
        });
    }

}
