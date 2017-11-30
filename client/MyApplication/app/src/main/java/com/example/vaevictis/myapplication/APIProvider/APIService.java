package com.example.vaevictis.myapplication.APIProvider;
import com.example.vaevictis.myapplication.user.User;

import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.http.*;

/**
 * Created by vaevictis on 17.11.17.
 */
public interface APIService {

    @POST("/auth")
    Call<User> signIn(@Body User user);
    @PUT("/auth")
    Call<ResponseBody> getToken(@Body User user, @Header("Authorization") String authHeader);
    @GET("/api/me")
    Call<ResponseBody> getMe(@Body User user, @Header("Authorization") String authHeader);
    @PUT("/api/users")
    Call<ResponseBody> updateMe(@Body User user, @Header("Authorization") String authHeader);
    @GET("/api/users")
    Call<ResponseBody> getAllUsers(@Body User user, @Header("Authorization") String authHeader);
}
