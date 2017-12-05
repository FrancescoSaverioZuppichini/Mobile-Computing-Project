package com.example.vaevictis.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.vaevictis.myapplication.GoogleAPI.GoogleAPIService;
import com.example.vaevictis.myapplication.location.LocationController;
import com.example.vaevictis.myapplication.user.UserController;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton askForHelpButton;
    LocationController locationController;
    YoYo.YoYoString animation;

    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        GoogleAPIService googleAPIService = new GoogleAPIService(this);
        locationController = new LocationController(this);
        googleAPIService.getClient();

        locationController.initialise();
        askForHelpButton = findViewById(R.id.main_phone);

        userController = new UserController(this);

        userController.getMe();
        System.out.println(UserController.user.getToken().getValue());

        addListenerOnButton();

//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                locationController.
//            }
//        });

//
//        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
//        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Settings");
//
////create the drawer and remember the `Drawer` result object
//        Drawer result = new DrawerBuilder()
//                .withActivity(this)
//                .withToolbar(toolbar)
//                .addDrawerItems(
//                        item1,
//                        new DividerDrawerItem(),
//                        item2
//                )
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        // do something with the clicked item :D
//
//                        return false;
//                    }
//                })
//                .build();

    }

    private void addListenerOnButton() {
        askForHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userController.askForHelp();

                int icon = userController.isCalling() ? R.drawable.ic_call_end_black_24px : R.drawable.ic_phone_black_24px;

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

            }
        });
    }
}
