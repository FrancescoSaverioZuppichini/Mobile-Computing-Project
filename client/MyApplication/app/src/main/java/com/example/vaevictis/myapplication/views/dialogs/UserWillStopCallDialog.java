package com.example.vaevictis.myapplication.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.vaevictis.myapplication.controllers.UserController;
import com.example.vaevictis.myapplication.views.fragments.HomeFragment;

/**
 * Created by vaevictis on 17.12.17.
 */
public class UserWillStopCallDialog extends DialogFragment {
    HomeFragment homeFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final UserController userController = new UserController(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure you want quit the call?");
        builder.setMessage("All the users around will be notified.")
                .setPositiveButton("STOP CALL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("STOPPPPP Call");
                        userController.endCall();
                        homeFragment.onAskForHelp();
//                        ((HomeFragment) getActivity().getFragmentManager().findFragmentById(R.layout.fragment_home)).onAskForHelp();
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

    public void setHomeFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }
}