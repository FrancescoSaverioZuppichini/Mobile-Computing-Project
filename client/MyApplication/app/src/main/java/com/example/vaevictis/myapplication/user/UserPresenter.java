package com.example.vaevictis.myapplication.user;

import android.util.Log;

import com.example.vaevictis.myapplication.APIProvider.APIProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaevictis on 15.11.17.
 */

public class UserPresenter implements UserContract.Presenter {
    @Override
    public void validateAndDoAuth(String email, String password) {
        User user = new User(email, password);
//        TODO client validate email and password -> check if they exists
        Call<User> res = APIProvider.getClient().signIn(user);

        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t);
            }
        });

    }

    @Override
    public void onDestroy() {

    }
}
