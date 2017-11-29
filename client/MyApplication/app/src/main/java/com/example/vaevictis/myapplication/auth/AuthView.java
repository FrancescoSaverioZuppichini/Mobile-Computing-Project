package com.example.vaevictis.myapplication.auth;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vaevictis.myapplication.R;

public class AuthView extends AppCompatActivity implements AuthContract.View {

    Button signIn;
    Button signUp;

    @Override
    public void showError(String error) {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);

        addListenerOnButton();

    }

    private void addListenerOnButton(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Clicked SignIn!", Toast.LENGTH_SHORT).show();
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
