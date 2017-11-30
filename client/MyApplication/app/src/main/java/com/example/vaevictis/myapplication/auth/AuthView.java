package com.example.vaevictis.myapplication.auth;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vaevictis.myapplication.user.UserController;
import com.example.vaevictis.myapplication.R;

public class AuthView extends AppCompatActivity{

    Button signIn;
    Button signUp;
    EditText emailField;
    EditText passwordField;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);

        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

        addListenerOnButton();

    }

    private void addListenerOnButton(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Clicked SignIn!", Toast.LENGTH_SHORT).show();

                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                UserController.validateAndDoLogIn(email, password);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Clicked SignUp!", Toast.LENGTH_SHORT).show();

                Intent switchToSignUp = new Intent(AuthView.this, RegisterView.class);
                startActivity(switchToSignUp);
            }
        });
    }
}
