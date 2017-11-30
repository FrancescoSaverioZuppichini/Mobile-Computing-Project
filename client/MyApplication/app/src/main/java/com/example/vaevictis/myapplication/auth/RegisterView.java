package com.example.vaevictis.myapplication.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vaevictis.myapplication.R;

public class RegisterView extends AppCompatActivity {
    Button signIn;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);

        addListenerOnButton();

    }

    private void addListenerOnButton(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                little hack, this activity can only be called from Auth, so let's go back
                onBackPressed();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Clicked SignUp!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
