package com.example.vaevictis.myapplication.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.xw.repo.BubbleSeekBar;

import java.util.List;

public class SettingFragment extends Fragment implements Validator.ValidationListener{
    View myView;

    private BubbleSeekBar seekBar;
    private Button confirmButton;

    @NotEmpty
    @Email
    EditText emailField;
//    @NotEmpty
    EditText passwordField;

    Spinner spinner;

    RadioButton userSelector;
    RadioButton volunteerSelector;

    Validator validator;
    UserController userController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_setting, container, false);

        validator = new Validator(this);
        validator.setValidationListener(this);

        userController = new UserController(getActivity());

        seekBar = myView.findViewById(R.id.radius_seeker);
        seekBar.setProgress(UserController.user.getPreferences().getRadiusInMeters());
        confirmButton = myView.findViewById(R.id.confirm);

        emailField =  myView.findViewById(R.id.email);

        emailField.setText(UserController.user.getEmail());

        passwordField = myView.findViewById(R.id.password);

        userSelector = myView.findViewById(R.id.radio_user);


        volunteerSelector = myView.findViewById(R.id.radio_volunteer);

        spinner = myView.findViewById(R.id.bloodSpinner);

        updateViews();
        addListenerOnButton();
        setUpBloodSpinner();

        return myView;
    }

    private void updateViews(){

        if(UserController.user.getRole().equals("USER")) {
            userSelector.setChecked(true);
        } else {
            volunteerSelector.setChecked(true);
        }
    }

    private void setUpBloodSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.bloodTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void addListenerOnButton() {
        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                System.out.println(progress);
                UserController.user.getPreferences().setRadiusInMeters(progress);

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
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
        UserController.user.setEmail( emailField.getText().toString());
//        String password = passwordField.getText().toString();

        userController.updateUser();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }


}