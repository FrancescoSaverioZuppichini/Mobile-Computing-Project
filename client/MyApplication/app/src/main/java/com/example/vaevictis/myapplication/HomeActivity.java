package com.example.vaevictis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.vaevictis.myapplication.GoogleAPI.GoogleAPIService;
import com.example.vaevictis.myapplication.user.UserController;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class HomeActivity extends AppCompatActivity {
    HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingFragment = new SettingFragment();

    Drawer myDrawer;

    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit();
        addDrawer();

        GoogleAPIService googleAPIService = new GoogleAPIService(this);
        googleAPIService.getClient();


        System.out.println(UserController.user.getToken().getValue());

    }

    private void addDrawer(){
        Toolbar toolbar = findViewById(R.id.toolbar);

        PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_home);
        final SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_settings);

//        TODO for color
//        .withHeaderBackground(R.drawable.header)

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.md_red_700)
                .addProfiles(
                        new ProfileDrawerItem().withEmail(UserController.user.getEmail()).withIcon(getResources().getDrawable(R.drawable.ic_61205))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        myDrawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        home,
                        new DividerDrawerItem(),
                        settings
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        System.out.println(position);
                        switch (position){
                            case(3):
                                System.out.println("SETTINGS");
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, settingFragment)
                                        .commit();
                                break;
                            case(1):
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, homeFragment)
                                        .commit();
                                System.out.println("HOME");
                        }
                        // do something with the clicked item :D

                        myDrawer.closeDrawer();

                        return true;

                    }
                })
                .build();
    }


}
