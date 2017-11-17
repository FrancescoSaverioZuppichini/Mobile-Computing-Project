package com.example.vaevictis.myapplication.APIProvider;
import com.example.vaevictis.myapplication.user.User;

import retrofit2.Retrofit;
import retrofit2.http.*;

/**
 * Created by vaevictis on 17.11.17.
 */
public interface APIService {
    @POST("auth/")
    void signIn(@Body User user);
}
