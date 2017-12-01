package com.example.vaevictis.myapplication.user;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.vaevictis.myapplication.APIProvider.APIProvider;
import com.example.vaevictis.myapplication.HomeActivity;
import com.example.vaevictis.myapplication.Token;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaevictis on 15.11.17.
 */

public class UserController {
    public static User user;

    private Context context;

    public UserController(Context context) {
        this.context = context;
    }

    public void doSignIn(String email, String password) {
        user = new User(email, password);
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
        user = new User(email, password);

        final Call<User> res = APIProvider.service.signUp(user);
        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {

                    User newUser = response.body();

                    newUser.setToken(user.getToken());

                    user = newUser;

                    Toast.makeText(context, "Account successfully created!", Toast.LENGTH_SHORT).show();

                    Intent goToHome = new Intent(context, HomeActivity.class);
                    context.startActivity(goToHome);

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

                    user = updatedUser;


                    Toast.makeText(context, "Position Updated", Toast.LENGTH_SHORT).show();

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


}
