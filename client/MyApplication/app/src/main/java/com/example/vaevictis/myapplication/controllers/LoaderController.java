package com.example.vaevictis.myapplication.controllers;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.vaevictis.myapplication.R;

/**
 * Created by vaevictis on 19.12.17.
 */

public class LoaderController {
    public static Context context;
    public static boolean isLoading = false;
    public static ProgressDialog progressDialog;

    public static void toogleLoad(){
        isLoading = !isLoading;
        if(isLoading){
            progressDialog = new ProgressDialog(context, R.style.MyTheme);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        }
        else if(progressDialog == null){
            System.out.println("SOMETHING WEIRD ");
        }
        else{
            progressDialog.dismiss();
        }
    }

}
