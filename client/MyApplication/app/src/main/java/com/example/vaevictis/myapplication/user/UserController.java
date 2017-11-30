package com.example.vaevictis.myapplication.user;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.vaevictis.myapplication.APIProvider.APIProvider;
import com.example.vaevictis.myapplication.HomeActivity;
import com.example.vaevictis.myapplication.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaevictis on 15.11.17.
 */

public class UserController {
    static User user;

    private Context context;

    public UserController(Context context) {
        this.context = context;
    }

    public void validateAndDoLogIn(String email, String password) {
        user = new User(email, password);
//        TODO client validate email and password -> check if they exists
        final Call<Token> res = APIProvider.service.getToken(user);
        res.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()) {

                    Token token = response.body();
                    user.setToken(token);

                    Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show();

                    Intent goToHome = new Intent(context, HomeActivity.class);
                    context.startActivity(goToHome);

                } else {
                    System.out.println("SOMETHING EXPLODED");
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });

    }

    public void onDestroy() {

    }
}
