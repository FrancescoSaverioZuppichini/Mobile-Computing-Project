package com.example.vaevictis.myapplication.views.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vaevictis.myapplication.APIProvider.APIProvider;
import com.example.vaevictis.myapplication.GoogleAPI.GoogleAPIService;
import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.controllers.UserController;
import com.example.vaevictis.myapplication.views.dialogs.UserAskForHelpDialog;
import com.example.vaevictis.myapplication.views.fragments.HelpFragment;
import com.example.vaevictis.myapplication.views.fragments.HomeFragment;
import com.example.vaevictis.myapplication.views.fragments.MyMapFragment;
import com.example.vaevictis.myapplication.views.fragments.SettingFragment;
import com.mikepenz.iconics.IconicsDrawable;
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
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.squareup.picasso.Picasso;

public class HomeActivity extends FragmentActivity {
    public HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingFragment = new SettingFragment();
    MyMapFragment myMapFragment = new MyMapFragment();
    public HelpFragment helpFragment = new HelpFragment();

    Drawer myDrawer;

    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userController = new UserController(this);

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit();
        addDrawer();

        GoogleAPIService googleAPIService = new GoogleAPIService(this);
        googleAPIService.getClient();

        isPermissionGranted();

        System.out.println(UserController.user.getToken().getValue());

    }

    public  boolean isPermissionGranted() {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission to call granted");
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Permission to call not granted");
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getIntent().getBooleanExtra("fromNotification", false)) {
//            be sure to load the fragment before everything
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();

            getIntent().removeExtra("fromNotification");
            UserAskForHelpDialog newFragment = new UserAskForHelpDialog();

            newFragment.show(getSupportFragmentManager(), "help");
        }

    }

    public void switchToFragment(Fragment fragment, boolean addToHistory){
        removeAll();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);

        if(addToHistory) transaction.addToBackStack(fragment.getClass().getName());

        transaction.commit();
    }

    public void removeAll(){
        for(Fragment f : getSupportFragmentManager().getFragments()) {
            if(f != null) {
                getSupportFragmentManager().beginTransaction().remove(f).commit();
            }
        }
    }


    private void addDrawer(){
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
            @Override
            public Drawable placeholder(Context ctx, String tag) {

                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.colorPrimaryDark).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.colorPrimary).sizeDp(56);
                }

                DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name();

                return super.placeholder(ctx, tag);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);

        PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_home);
        final SecondaryDrawerItem map = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_map);
        final SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_settings);
        final SecondaryDrawerItem logout = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_logout);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimary)
                .addProfiles(
                        new ProfileDrawerItem().withEmail(UserController.user.getEmail()).withIcon(APIProvider.BASE_URL + UserController.user.getAvatar()).withIdentifier(100)
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
                        map,
                        new DividerDrawerItem(),
                        settings,
                        logout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        System.out.println(position);
                        switch (position){
                            case(2):
                                UserController.currentMap = null;
                                UserController.hasAlreadyOpenMap = false;
                                removeAll();
                                System.out.println("MAP");
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, myMapFragment)
                                        .commit();
                                break;
                            case(4):
                                System.out.println("SETTINGS");
                                removeAll();
                                UserController.currentMap = null;

                                getFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, settingFragment)
                                        .commit();
                                break;
                            case(1):
                                removeAll();
                                UserController.currentMap = null;
                                if(UserController.isHelping) {
                                    switchToFragment(helpFragment, true);
                                } else {
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.fragment_container, homeFragment)
                                            .commit();
                                }
                                System.out.println("HOME");
                                break;
                            case(5):
                                userController.doLogout();
                                onBackPressed();
                                System.out.println("LOGOUT");

                        }
                        myDrawer.closeDrawer();

                        return true;

                    }
                })
                .build();
    }


}
