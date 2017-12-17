package com.example.vaevictis.myapplication.APIProvider;

import com.example.vaevictis.myapplication.models.Token;
import com.example.vaevictis.myapplication.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by vaevictis on 17.11.17.
 */
public interface APIService {

    @POST("/auth")
    Call<User> signUp(@Body User user);
    @PUT("/auth")
    Call<Token> getToken(@Body User user);
    @GET("/api/users/me")
    Call<Token> updateToken(@Body Token token);
    @GET("/api/users/me")
    Call<User> getMe(@Header("Authorization") String authHeader);
    @PUT("/api/users")
    Call<User> updateMe(@Body User user, @Header("Authorization") String authHeader);
    @GET("/api/users")
    Call<List<User>> getAllUsers(@Body User user, @Header("Authorization") String authHeader);
}
