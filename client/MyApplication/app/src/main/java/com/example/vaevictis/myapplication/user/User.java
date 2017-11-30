package com.example.vaevictis.myapplication.user;

import com.example.vaevictis.myapplication.Token;

/**
 * Created by vaevictis on 15.11.17.
 */

public class User {
    private String email;
    private String password;
    private Token token;

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

}
