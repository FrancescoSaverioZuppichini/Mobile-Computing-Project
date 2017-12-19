package com.example.vaevictis.myapplication.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.vaevictis.myapplication.controllers.UserController;

/**
 * Created by vaevictis on 07.12.17.
 */

public class UserAskForHelpDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final UserController userController = new UserController(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
         builder.setTitle("User " + UserController.fromUser.getEmail() +  " call for help!");
            builder.setMessage(UserController.fromUser.getDist() + " meters from you")
                    .setPositiveButton("HELP HIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            userController.willHelp(UserController.fromUser);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            UserController.fromUser = null;
                            UserController.isHelping = false;
                        }
                    });

        return builder.create();
    }
}
