package com.example.vaevictis.myapplication.APIProvider;
import com.example.vaevictis.myapplication.user.User;

import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.http.*;

/**
 * Created by vaevictis on 17.11.17.
 */
public interface APIService {
    @POST("auth/")
    Call<ResponseBody> signIn(@Body User user);
}
