package com.example.vaevictis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.vaevictis.myapplication.GoogleAPI.GoogleAPIService;
import com.example.vaevictis.myapplication.location.LocationController;

public class HomeActivity extends AppCompatActivity {
    LocationController locationController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GoogleAPIService googleAPIService = new GoogleAPIService(this);
        locationController = new LocationController(this);
        googleAPIService.getClient();

        locationController.initialise();
        Button button = (Button) findViewById(R.id.position);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                locationController.
//            }
//        });



    }
}
