package com.example.vaevictis.myapplication.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.controllers.UserController;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class AuthView extends AppCompatActivity implements Validator.ValidationListener {

    Button signIn;
    Button signUp;

    @NotEmpty
    @Email
    EditText emailField;
    @NotEmpty
    EditText passwordField;

    Validator validator;

    UserController userController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

        userController = new UserController(this);

        userController.signInIfAlreadyAToken();

        validator = new Validator(this);
        validator.setValidationListener(this);

        signIn =  findViewById(R.id.signIn);
        signUp =  findViewById(R.id.signUp);

        emailField =  findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        addListenerOnButton();

    }

    private void addListenerOnButton(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent goToHome = new Intent(AuthView.this, HomeActivity.class);
//                startActivity(goToHome);
//            TODO ricordarsi di rimetterlo senno non va 1 cazzo
                validator.validate();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent switchToSignUp = new Intent(AuthView.this, RegisterView.class);
                startActivity(switchToSignUp);
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        userController.doSignIn(email, password);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
