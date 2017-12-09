package com.example.vaevictis.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {
    private View myView;
    private GoogleMap googleMapInstance;
    static public GoogleMap map = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.maps, container, false);

        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapsfragment);

        mapFrag.getMapAsync(this);
//        ((MapFragment) getFragmentManager().findFragmentById(R.id.mapsfragment)).getMapAsync(this);

        return myView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.clear();
        System.out.println("MAP READY");
    }
}
