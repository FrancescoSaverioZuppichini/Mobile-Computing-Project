package com.example.vaevictis.myapplication.APIProvider;
import com.example.vaevictis.myapplication.user.User;

import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.http.*;

/**
 * Created by vaevictis on 17.11.17.
 */
public interface APIService {
    String SERVER_URL = "localhost:3000/";

    @POST(SERVER_URL + "/auth")
    Call<ResponseBody> signIn(@Body User user);
    @PUT(SERVER_URL + "/auth")
    Call<ResponseBody> getToken(@Body User user, @Header("Authorization") String authHeader);
    @GET(SERVER_URL + "/api//me")
    Call<ResponseBody> getMe(@Body User user, @Header("Authorization") String authHeader);
    @PUT(SERVER_URL + "/api/users")
    Call<ResponseBody> updateMe(@Body User user, @Header("Authorization") String authHeader);
    @GET(SERVER_URL + "/api/users")
    Call<ResponseBody> getAllUsers(@Body User user, @Header("Authorization") String authHeader);
}
