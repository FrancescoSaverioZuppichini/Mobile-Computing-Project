package com.example.vaevictis.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vaevictis on 30.11.17.
 */

final public class Utils {

    static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    static boolean isValidPassword(String pass) {
        return pass != null && pass.length() > 0;
    }
}
