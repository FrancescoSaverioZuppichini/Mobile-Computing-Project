package com.example.vaevictis.myapplication.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.controllers.UserController;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class RegisterView extends AppCompatActivity implements Validator.ValidationListener{
    Button signIn;
    Button signUp;

    @NotEmpty
    @Email
    EditText emailField;

    @NotEmpty
    EditText passwordField;

    Spinner spinner;

    RadioButton userSelector;
    RadioButton volunteerSelector;

    Validator validator;

    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        
        userController = new UserController(this);

        validator = new Validator(this);
        validator.setValidationListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);

        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

        userSelector = (RadioButton) findViewById(R.id.radio_user);
        volunteerSelector = (RadioButton) findViewById(R.id.radio_volunteer);

        spinner = (Spinner) findViewById(R.id.bloodSpinner);

        addListenerOnButton();
        setUpBloodSpinner();
    }

    private void setUpBloodSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bloodTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

                validator.validate();
            }
        });

        userSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserController.user.setRole("USER");
            }
        });

        volunteerSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserController.user.setRole("VOLUNTEER");
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        userController.doSignUp(email, password);

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
