package com.example.vaevictis.myapplication.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.vaevictis.myapplication.GoogleAPI.GoogleAPIService;
import com.example.vaevictis.myapplication.MyMapFragment;
import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.user.UserController;
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

public class HomeActivity extends AppCompatActivity {
    HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingFragment = new SettingFragment();
    MyMapFragment myMapFragment = new MyMapFragment();

    Drawer myDrawer;

    UserController userController;

    public MyMapFragment getMyMapFragment() {
        return myMapFragment;
    }

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


        System.out.println(UserController.user.getToken().getValue());

    }

    private void addDrawer(){
        Toolbar toolbar = findViewById(R.id.toolbar);

        PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_home);
        final SecondaryDrawerItem map = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_map);
        final SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_settings);
        final SecondaryDrawerItem logout = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_logout);

//        TODO for color
//        .withHeaderBackground(R.drawable.header)

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.md_red_700)
                .addProfiles(
                        new ProfileDrawerItem().withEmail(UserController.user.getEmail())
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load("https://t3.ftcdn.net/jpg/01/06/07/16/240_F_106071621_UwCztl7yyMbVNSMijfuYyZrzbtmoxJPH.jpg").placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
            @Override
            public Drawable placeholder(Context ctx, String tag) {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
                }

                //we use the default one for
                DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name();

                return super.placeholder(ctx, tag);
            }
        });

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
                                System.out.println("MAP");
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, myMapFragment)
                                        .commit();
                                break;
                            case(4):
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
                                break;
                            case(5):
                                userController.doLogout();
                                onBackPressed();

                                System.out.println("LOGOUT");

                        }
                        // do something with the clicked item :D

                        myDrawer.closeDrawer();

                        return true;

                    }
                })
                .build();
    }


}
