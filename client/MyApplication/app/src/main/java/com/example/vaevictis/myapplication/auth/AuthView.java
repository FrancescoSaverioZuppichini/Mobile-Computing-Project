package com.example.vaevictis.myapplication.auth;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vaevictis.myapplication.user.UserContract;
import com.example.vaevictis.myapplication.user.UserPresenter;
import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.user.User;

public class AuthView extends AppCompatActivity implements UserContract.View {

    Button signIn;
    Button signUp;
    EditText emailField;
    EditText passwordField;

    UserPresenter presenter = new UserPresenter();
    @Override
    public void showError(String error) {

    }

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

                presenter.validateAndDoAuth(email, password);
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
