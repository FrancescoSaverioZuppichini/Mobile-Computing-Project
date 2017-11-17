package com.example.vaevictis.myapplication.APIProvider;

import retrofit2.Retrofit;

/**
 * Created by vaevictis on 17.11.17.
 */

public final class APIProvider {

    private String BASE_URL = "localhost:3000/";

    private APIService service = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create(APIService.class);

}
