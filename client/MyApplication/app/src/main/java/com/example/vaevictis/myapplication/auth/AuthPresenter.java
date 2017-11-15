package com.example.vaevictis.myapplication.auth;

/**
 * Created by vaevictis on 15.11.17.
 */

public interface AuthPresenter {

    void validateAndDoAuth(String email, String password);

    void onDestroy();
}
