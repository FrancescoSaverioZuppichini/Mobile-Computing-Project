package com.example.vaevictis.myapplication.auth;

/**
 * Created by vaevictis on 15.11.17.
 */

public interface AuthContract {

    interface View {

        void showError(String error);
    }

    interface Presenter {

        void validateAndDoAuth(String email, String password);

        void onDestroy();
    }

}
