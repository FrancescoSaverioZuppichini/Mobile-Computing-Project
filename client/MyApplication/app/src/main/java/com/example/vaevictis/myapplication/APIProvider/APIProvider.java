package com.example.vaevictis.myapplication.APIProvider;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vaevictis on 17.11.17.
 */

import okhttp3.OkHttpClient;

public class APIProvider {
    public static final String BASE_URL = "http://192.168.1.20:3000/";
    //    public static final String BASE_URL = "http://10.62.161.50:3000/";
//
    private static APIService sRetrofit = null;

    public static final APIService service = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService.class);


    public static APIService getClient() {
        if (sRetrofit == null) {

            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .build();

            sRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(APIService.class);

        }

        return sRetrofit;
    }
}
