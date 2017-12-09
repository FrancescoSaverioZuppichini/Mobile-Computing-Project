package com.example.vaevictis.myapplication.GoogleAPI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by vaevictis on 01.12.17.
 */

public class GoogleAPIService implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    static final public String API_KEY = "AIzaSyDMCaYL4t_p_8GEkiMbKf-SqGcG78QFP8g";
    private Context context;
    private GoogleApiClient client;

    public GoogleAPIService(Context context) {

        this.context = context;
        client = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        client.connect();
    }

    public GoogleApiClient getClient() {
        return client;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(context, "CONNNECTED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(context, "CONNNECTED SUSPENDED", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(context, "CONNNECTION FAILEED", Toast.LENGTH_SHORT).show();

    }

//    private GeoApiContext getGeoContext() {
//        GeoApiContext geoApiContext = new GeoApiContext();
//        return geoApiContext.setQueryRateLimit(3)
//                .setApiKey(API_KEY)
//                .setConnectTimeout(1, TimeUnit.SECONDS)
//                .setReadTimeout(1, TimeUnit.SECONDS)
//                .setWriteTimeout(1, TimeUnit.SECONDS);
//    }

}
