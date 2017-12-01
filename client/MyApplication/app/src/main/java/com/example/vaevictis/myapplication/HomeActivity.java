package com.example.vaevictis.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vaevictis.myapplication.GoogleAPI.GoogleAPIService;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GoogleAPIService googleAPIService = new GoogleAPIService(this);

        googleAPIService.getClient();

    }
}
