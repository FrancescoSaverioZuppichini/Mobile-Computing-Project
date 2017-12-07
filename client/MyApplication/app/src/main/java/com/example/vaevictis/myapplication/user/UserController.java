package com.example.vaevictis.myapplication.user;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.vaevictis.myapplication.APIProvider.APIProvider;
import com.example.vaevictis.myapplication.APIProvider.SocketClient;
import com.example.vaevictis.myapplication.UserAskForHelpDialog;
import com.example.vaevictis.myapplication.auth.Token;
import com.example.vaevictis.myapplication.home.HomeActivity;
import com.google.gson.Gson;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaevictis on 15.11.17.
 */

public class UserController {
    public static User user = new User();
    public static User fromUser;
    private Context context;
    private boolean isCalling = false;

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

                    Intent goToHome = new Intent(context, HomeActivity.class);
                    context.startActivity(goToHome);

                } else {

                    DynamicToast.makeError(context, "Email or Password not valid").show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });

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

    public void updateUser(){

        final Call<User> res = APIProvider.service.updateMe(user, "Bearer " + user.getToken().getValue());

        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    User updatedUser = response.body();

                    updatedUser.setToken(user.getToken());
                    updatedUser.setEmailAndPassword(user.getEmail(), user.getPassword());

                    user = updatedUser;
                    System.out.println(user.getEmail() + ' '  + user.getPassword());

                    System.out.println("POSITION UPDATED");
//                    Toast.makeText(context, "Position Updated", Toast.LENGTH_SHORT).show();

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
                    SocketClient.socket.emit("test","hiii");


                } else {
                    System.out.println(response.errorBody());
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void askForHelp(){

        isCalling = !isCalling;

        if(isCalling) {
            SocketClient.socket.emit("help","HELP");

            SocketClient.socket.on("help_request", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    JSONObject obj = (JSONObject)args[0];

                    try {
                        final JSONObject from = (JSONObject) obj.get("from");
                        Gson gson = new Gson();

                        fromUser = gson.fromJson(from.toString(), User.class);

                        Handler toastHandler = new Handler(Looper.getMainLooper());

                        toastHandler.post(new Runnable() {
                            public void run() {
//                                    andoird is shit LOL
                                    FragmentActivity activity = (FragmentActivity) context;
                                    FragmentManager manager = activity.getSupportFragmentManager();

                                    UserAskForHelpDialog newFragment = new UserAskForHelpDialog();

                                    newFragment.show(manager, "help");

//                                    DynamicToast.makeWarning(context, "User " + from.get("email") + " ask for help!",Toast.LENGTH_LONG).show();}

                            }
                        });

                    } catch (JSONException e) {
                        System.out.println(e.getCause());
                    }

                }
            });

//            Toast.makeText(context, "HELPPPPPPPPP", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("ALL GOOD");
//            Toast.makeText(context, "All good", Toast.LENGTH_SHORT).show();
        }

        SocketClient.socket.on("help_accepted_success", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("HEEEEELPPP ACCEPTED");

                JSONObject obj = (JSONObject)args[0];
                try {

                    final JSONObject from = (JSONObject) obj.get("from");

                    Gson gson = new Gson();

                    final User userThatWillHelp = gson.fromJson(from.toString(), User.class);
//                    TODO add a counter on users fab
                    Handler toastHandler = new Handler(Looper.getMainLooper());

                    toastHandler.post(new Runnable() {
                        public void run() {
                            DynamicToast.makeSuccess(context, "User " + userThatWillHelp.getEmail() +  " will help you", Toast.LENGTH_LONG).show();

                        }
                    });

                } catch (JSONException e) {
                    System.out.println(e.getCause());
                }
            }
        });
    }

    public boolean isCalling(){
        return this.isCalling;
    }

}
