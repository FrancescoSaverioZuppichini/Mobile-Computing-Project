package com.example.vaevictis.myapplication.user;

import android.util.Log;

import com.example.vaevictis.myapplication.APIProvider.APIProvider;
import com.example.vaevictis.myapplication.Token;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaevictis on 15.11.17.
 */

final public class UserController {

    static public void validateAndDoLogIn(String email, String password) {
        User user = new User(email, password);
//        TODO client validate email and password -> check if they exists
        final Call<Token> res = APIProvider.service.getToken(user);

        res.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()) {

                    Token token = response.body();

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
