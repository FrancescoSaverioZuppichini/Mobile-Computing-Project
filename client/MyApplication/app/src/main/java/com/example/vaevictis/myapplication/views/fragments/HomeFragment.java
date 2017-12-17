package com.example.vaevictis.myapplication.views.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andremion.counterfab.CounterFab;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.controllers.LocationController;
import com.example.vaevictis.myapplication.controllers.UserController;
import com.example.vaevictis.myapplication.views.dialogs.UserWillStopCallDialog;


public class HomeFragment extends Fragment {
    View myView;

    FloatingActionButton askForHelpButton;
    LocationController locationController;
    CounterFab peopleCounterFab;

    YoYo.YoYoString animation;
    UserController userController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home, container, false);

        userController = new UserController(myView.getContext());
        userController.bindOnSocketsEvent();
        locationController = new LocationController(myView.getContext());
        locationController.initialise();

        askForHelpButton = myView.findViewById(R.id.main_phone);
        peopleCounterFab = myView.findViewById(R.id.people);
        addListenerOnButton();

        return myView;
    }

    public void onAskForHelp(){

        int icon = userController.isCalling() ? R.drawable.ic_call_end_black_24px : R.drawable.ic_phone_black_24px;
        int color = userController.isCalling() ? R.color.md_red_500 : R.color.md_green_500 ;

        if(!userController.isCalling()){
            System.out.println("STOP");
            animation.stop(false);
        }
        else {
            animation = YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(YoYo.INFINITE)
                    .playOn(askForHelpButton);
        }

        askForHelpButton.setImageResource(icon);
        askForHelpButton.setBackgroundTintList(getResources().getColorStateList(color));
    }

    private void addListenerOnButton() {
        askForHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!UserController.isCalling) {
                        userController.call();
                        onAskForHelp();
                        UserController.isCalling = true;
                } else {
                    UserWillStopCallDialog newFragment = new UserWillStopCallDialog();
                    newFragment.setHomeFragment(HomeFragment.this);
                    FragmentActivity activity = (FragmentActivity) getContext();
                    FragmentManager manager = activity.getSupportFragmentManager();
                    newFragment.show(manager, "will_end_call");
                }

                }
        });

        peopleCounterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction goToUsers =  getFragmentManager().beginTransaction();

                goToUsers.replace(R.id.fragment_container, new UsersFragment())
                        .addToBackStack("users")
                        .commit();
            }
        });


    }
}