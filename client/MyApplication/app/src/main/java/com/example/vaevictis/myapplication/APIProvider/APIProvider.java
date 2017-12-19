package com.example.vaevictis.myapplication.APIProvider;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vaevictis on 17.11.17.
 */

public class APIProvider {
//    gf'shouse
//    public static final String BASE_URL = "http://192.168.1.66:3000/";
//    casa verona
//      public static final String BASE_URL = "http://192.168.1.193:3000/";
//    my house lugano
//    public static final String BASE_URL = "http://192.168.1.20:3000/";
//    uni
    public static final String BASE_URL = "http://10.62.132.253:3000/";
//    casa alessia
//    public static final String BASE_URL = "http://192.168.8.107:3000";

    private static APIService sRetrofit = null;

    public static final APIService service = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService.class);
}
