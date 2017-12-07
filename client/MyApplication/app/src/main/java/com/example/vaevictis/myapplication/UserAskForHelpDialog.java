package com.example.vaevictis.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.vaevictis.myapplication.APIProvider.SocketClient;
import com.example.vaevictis.myapplication.user.UserController;
import com.google.gson.Gson;

/**
 * Created by vaevictis on 07.12.17.
 */

public class UserAskForHelpDialog extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            System.out.println(UserController.fromUser);
            builder.setTitle("User " + UserController.fromUser.getEmail() +  " call for help!");
            builder.setMessage(UserController.fromUser.getDist() + " meters from you")
                    .setPositiveButton("HELP HIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Gson gson = new Gson();
                            // FIRE ZE MISSILES!
                            SocketClient.socket.emit("help_accepted",gson.toJson(UserController.fromUser));

                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

        // Create the AlertDialog object and return it

        return builder.create();
    }
}
