package com.example.vaevictis.myapplication.controllers;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.directions.route.Routing;
import com.example.vaevictis.myapplication.APIProvider.APIProvider;
import com.example.vaevictis.myapplication.APIProvider.SocketClient;
import com.example.vaevictis.myapplication.GoogleAPI.GoogleAPIService;
import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.Utils;
import com.example.vaevictis.myapplication.models.RawLocation;
import com.example.vaevictis.myapplication.models.Token;
import com.example.vaevictis.myapplication.models.User;
import com.example.vaevictis.myapplication.views.activities.HomeActivity;
import com.example.vaevictis.myapplication.views.dialogs.UserAskForHelpDialog;
import com.example.vaevictis.myapplication.views.dialogs.UserWillStopCallDialog;
import com.example.vaevictis.myapplication.views.dialogs.UserWillStopHelpDialog;
import com.example.vaevictis.myapplication.views.fragments.HelpFragment;
import com.example.vaevictis.myapplication.views.fragments.UsersFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaevictis on 15.11.17.
 */

public class UserController {
    public static boolean isLoggedIn = false;
    final static String PREF_KEY = "findMyCross";
    public static boolean isHelping = false;
    public static User user = new User();
    public static User fromUser;
    public static ArrayList<User> usersThatHelps = new ArrayList<>();
    public static GoogleMap currentMap;
    public static Marker myMarker;
    public static Marker fromMarker;
    public static boolean hasAlreadyOpenMap = false;
    private Context context;
    public static boolean isCalling = false;

    public UserController(Context context) {
        this.context = context;
    }

    public void doSignIn(String email, String password) {
        user.setEmailAndPassword(email, password);
        System.out.println(email + ' '  + password);
        final Call<Token> res = APIProvider.service.getToken(user);

        res.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()) {

                    Token token = response.body();
                    user.setToken(token);

                    DynamicToast.makeSuccess(context, "Login successful!").show();

                    isLoggedIn = true;

                    SharedPreferences pref = context.getSharedPreferences(PREF_KEY, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("token", token.getValue());
                    editor.apply();

                    getMe();

                } else {

                    DynamicToast.makeError(context, "Email or Password not valid").show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });

    }

    public void signInIfAlreadyAToken(){
        SharedPreferences pref = context.getSharedPreferences(PREF_KEY, 0);
        String token = pref.getString("token", null);

        if(token != null) {
            user.setToken(new Token(token));
            isLoggedIn = true;
            getMe();
        }
    }

    public void doSignUp(String email, String password) {
        user.setEmailAndPassword(email, password);

        final Call<User> res = APIProvider.service.signUp(user);
        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {

                    DynamicToast.makeSuccess(context, "Account successfully created!").show();

                    doSignIn(user.getEmail(), user.getPassword());

                } else {
                    System.out.println("SOMETHING EXPLODED");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void doLogout(){
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, 0);
        preferences.edit().remove("token").apply();
        user = new User();
    }

    public void updateMap(){
        if(currentMap != null) {

            System.out.println("UpdateMap");

            if(!hasAlreadyOpenMap) {
                CameraUpdate center = CameraUpdateFactory.newLatLng(user.getLatLng());
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

                currentMap.moveCamera(center);
                currentMap.animateCamera(zoom);

            } else {
                myMarker.remove();
            }

            hasAlreadyOpenMap = true;

//          Little hack -> TODO: properly change the map reference and update a flag to nofity we have a new map
            myMarker = currentMap.addMarker(new MarkerOptions().position(user.getLatLng()).title("You"));

            myMarker.showInfoWindow();

//            user.setMarker(myMarker);
//            System.out.println("updated user marker");

            if(fromUser != null){
                System.out.println(fromUser.marker);
                    if(HelpFragment.toHelpMaker == null){
                        HelpFragment.getAndDrawCustomMaker(fromUser, "-red.png", context);

                    } else {
                        HelpFragment.toHelpMaker.setPosition(fromUser.getLatLng());
                    }

            }
//
//            for(User userThatHelps: usersThatHelps){
//
// userThatHelps.marker = currentMap.addMarker(new MarkerOptions().position(userThatHelps.getLatLng()));
//                HelpFragment.getAndDrawCustomMaker(fromUser, ".png", context);
//
//            }


        }
    }

    public void makePhoneCall(){
        String phoneNumber = "118";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("DIOCANE");
            return;
        }
        context.startActivity(callIntent);
    }

    public void updateUser(){
        if(!isLoggedIn) {
            return;
        }

        final Call<User> res = APIProvider.service.updateMe(user, "Bearer " + user.getToken().getValue());

        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    User updatedUser = response.body();

                    updatedUser.setToken(user.getToken());
                    updatedUser.setEmailAndPassword(user.getEmail(), user.getPassword());

                    user = updatedUser;

                    System.out.println("POSITION UPDATED");

                    updateMap();

                    Gson gson = new Gson();
                    SocketClient.socket.emit("update", gson.toJson(user));
//                    Toast.makeText(context, "Position Updated", Toast.LENGTH_SHORT).show()
                }
                else {
                    System.out.println(response.errorBody());
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void getMe(){
        final Call<User> res = APIProvider.service.getMe("Bearer " + user.getToken().getValue());

        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    User me = response.body();

                    me.setToken(user.getToken());
                    user = me;

                    System.out.println(user.getEmail() + ' ' + user.getPassword());
                    SocketClient.start();
                    SocketClient.socket.connect();
                    Intent goToHome = new Intent(context, HomeActivity.class);
//                    goToHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(goToHome);

                } else {

                    switch (response.code()){
                        case 401:
//                            token is old -> just remove it
                            user.setToken(null);

                    }
                    System.out.println(response.code());
                    System.out.println(response.errorBody());
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    public void createHelpRequestNotification(){

        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "ANDROID_SHIT";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setContentTitle("Help request!")
                .setContentText(fromUser.getEmail() + " request help!")
                .setSmallIcon(R.drawable.ic_phone_black_24px)
                .setChannelId(CHANNEL_ID);
        Intent resultIntent = new Intent(context, HomeActivity.class).putExtra("fromNotification", true);;

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        notification.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);

        mNotificationManager.notify(notifyID , notification.build());
    }

    public void displayRoutes(HelpFragment context){
        LatLng here = new LatLng(user.getLocation().getLatitude(), user.getLocation().getLongitude());
//            TODO create a helper method to automatically parse and return LatLng
        RawLocation rawLocation = fromUser.getLocation();
        if(rawLocation == null) return;
        LatLng usi = new LatLng(rawLocation.getLatitude(),rawLocation.getLongitude());

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(context)
                .waypoints(here, usi)
                .key(GoogleAPIService.API_KEY)
                .build();
        routing.execute();

    }

    public void willHelp(User who){
        Gson gson = new Gson();

        SocketClient.socket.emit("help_accepted",gson.toJson(who));

        ((HomeActivity) context).switchToFragment(new HelpFragment(), false);

    }

    public void willStopHelp(){
        Handler dialogHandler = new Handler(Looper.getMainLooper());

        dialogHandler.post(new Runnable() {
            public void run() {
                FragmentActivity activity = (FragmentActivity) context;
                FragmentManager manager = activity.getSupportFragmentManager();

                UserWillStopHelpDialog newFragment = new UserWillStopHelpDialog();

                newFragment.show(manager, "will_stop_help");
            }
        });
    }

    public void stopHelp(){
        Gson gson = new Gson();
        ((HomeActivity) context).removeAll();
        ((HomeActivity) context).getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,  ((HomeActivity) context).homeFragment)
                .commit();
        SocketClient.socket.emit("help_stop_user", gson.toJson(fromUser));
        fromUser = null;
        currentMap = null;
        hasAlreadyOpenMap = false;
        HelpFragment.toHelpMaker = null;

    }

    public void removeUserThatWasHelpingYou(User userThatStopHelpYou){
        for(User user: usersThatHelps) {
            if(user.getEmail().equals(userThatStopHelpYou.getEmail())) {
                usersThatHelps.remove(user);
            }
        }
    }

    public void bindOnSocketsEvent(){
        SocketClient.socket.on("help_request", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                if(isCalling) return;
                JSONObject obj = (JSONObject)args[0];
                System.out.println("help_request");
                try {

                    final JSONObject from = (JSONObject) obj.get("from");
                    Gson gson = new Gson();

                    User userToHelp = gson.fromJson(from.toString(), User.class);

                    if(fromUser != null && userToHelp.getEmail().equals(fromUser.getEmail())) return;
                    if(fromUser == null) fromUser = userToHelp;

                    isHelping = true;

                    createHelpRequestNotification();

                    Handler toastHandler = new Handler(Looper.getMainLooper());
//                        TODO add a flag to avoid spam
                    toastHandler.post(new Runnable() {
                        public void run() {
                            FragmentActivity activity = (FragmentActivity) context;
                            FragmentManager manager = activity.getSupportFragmentManager();

                            UserAskForHelpDialog newFragment = new UserAskForHelpDialog();

                            newFragment.show(manager, "help");
                        }
                    });

                } catch (JSONException e) {
                    System.out.println(e.getCause());
                }

            }
        });

        SocketClient.socket.on("help_stop_user_success", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject)args[0];
                System.out.println("help_stop_user_success");
                try {

                    final JSONObject from = (JSONObject) obj.get("from");
                    Gson gson = new Gson();

                    User userThatStopHelpYou = gson.fromJson(from.toString(), User.class);

                    System.out.println("STOPPED HELP YOU:" + userThatStopHelpYou.getEmail());

                    if(!Utils.checkIfUserIsAlreadyHelping(userThatStopHelpYou, usersThatHelps)) return;

                    removeUserThatWasHelpingYou(userThatStopHelpYou);

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(UsersFragment.adapter != null){
//                                TODO check it
                                UsersFragment.adapter.notifyDataSetChanged();
                            }
                        }
                    });

                    Handler counterFabHandler = new Handler(Looper.getMainLooper());
                    counterFabHandler.post(new Runnable() {
                        public void run() {
                            CounterFab counterFab = ((Activity) context).findViewById(R.id.people);
                            if(counterFab != null){ counterFab.decrease(); }
                        }
                    });
                } catch (JSONException e) {
                    System.out.println(e.getCause());
                }

            }
        });

        SocketClient.socket.on("update_user", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject)args[0];
                System.out.println("update_user");
                try {

                    final JSONObject from = (JSONObject) obj.get("from");
                    Gson gson = new Gson();

                    User updatedUser = gson.fromJson(from.toString(), User.class);
                    if(fromUser != null){
                        if(fromUser.getEmail().equals(updatedUser.getEmail())){
                            System.out.println("DIOCANE FROM USER");
                            updatedUser.marker = fromUser.marker;
                            fromUser = updatedUser;
                        }
                    }
                    for(User user: usersThatHelps){
                        if(user.getEmail().equals(updatedUser.getEmail())){
                            System.out.println("DIOCANE USER THAT HELPS");
                            user = updatedUser;
                        }
                    }


                    Handler toastHandler = new Handler(Looper.getMainLooper());

                    toastHandler.post(new Runnable() {
                        public void run() {
                            updateMap();
                        }
                        });

                } catch (JSONException e) {
                    System.out.println(e.getCause());
                }

            }
        });

        SocketClient.socket.on("help_accepted_success", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject obj = (JSONObject)args[0];
                try {

                    final JSONObject from = (JSONObject) obj.get("from");

                    Gson gson = new Gson();

                    final User userThatWillHelp = gson.fromJson(from.toString(), User.class);

                    if(Utils.checkIfUserIsAlreadyHelping(userThatWillHelp, usersThatHelps)) return;

                    usersThatHelps.add(userThatWillHelp);

                    Handler toastHandler = new Handler(Looper.getMainLooper());

                    toastHandler.post(new Runnable() {
                        public void run() {
                            CounterFab counterFab =  ((Activity) context).findViewById(R.id.people);
                            counterFab.increase(); // Increase the current count value by 1
                            DynamicToast.makeSuccess(context, "User " + userThatWillHelp.getEmail() +  " will help you", Toast.LENGTH_LONG).show();

                        }
                    });

                } catch (JSONException e) {
                    System.out.println(e.getCause());
                }
            }
        });

        SocketClient.socket.on("help_end_success", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                if(isCalling) return;
                JSONObject obj = (JSONObject)args[0];
                System.out.println("help_end_success");
                stopHelp();
                Handler toastHandler = new Handler(Looper.getMainLooper());
//                        TODO add a flag to avoid spam
                toastHandler.post(new Runnable() {
                    public void run() {
                        DynamicToast.makeWarning(context, "The users does not require help anymore", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    public void endCall(){
        usersThatHelps = new ArrayList<>();
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(UsersFragment.adapter != null){
//                                TODO check it
                    UsersFragment.adapter.notifyDataSetChanged();
                }
                CounterFab counterFab =  ((Activity) context).findViewById(R.id.people);
                counterFab.setCount(0);
            }
        });

        Gson gson = new Gson();
        isCalling = false;

        SocketClient.socket.emit("help_end", gson.toJson(user));
    }

    public void willEndCall(){
        Handler dialogHandler = new Handler(Looper.getMainLooper());

        dialogHandler.post(new Runnable() {
            public void run() {
                FragmentActivity activity = (FragmentActivity) context;
                FragmentManager manager = activity.getSupportFragmentManager();

                UserWillStopCallDialog newFragment = new UserWillStopCallDialog();

                newFragment.show(manager, "will_end_call");
            }
        });
    }

    public void call(){
        SocketClient.socket.emit("help","HELP");
        makePhoneCall();
        isCalling = true;
    }

    public void askForHelp(){


        if(!isCalling) {
            SocketClient.socket.emit("help","HELP");
            isCalling = true;
        } else {
            willEndCall();
        }
    }

    public boolean isCalling(){
        return this.isCalling;
    }

}
