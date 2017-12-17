package com.example.vaevictis.myapplication.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.vaevictis.myapplication.controllers.UserController;

/**
 * Created by vaevictis on 16.12.17.
 */

public class UserWillStopHelpDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final UserController userController = new UserController(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure you want stop helping " + UserController.fromUser.getEmail());
        builder.setMessage("By stopping helping a user you won't be able to reach him again.")
                .setPositiveButton("STOP HELP", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("STOPPPPP HELLLLLLLLPPP");
                        userController.stopHelp();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog -> do nothing
                    }
                });
        return builder.create();
    }
}