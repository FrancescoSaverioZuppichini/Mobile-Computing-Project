package com.example.vaevictis.myapplication.user;

/**
 * Created by vaevictis on 15.11.17.
 */

public interface UserContract {

    interface View {

        void showError(String error);
    }

    interface Presenter {

        void  validateAndDoAuth(String email, String password);

        void onDestroy();
    }

}
