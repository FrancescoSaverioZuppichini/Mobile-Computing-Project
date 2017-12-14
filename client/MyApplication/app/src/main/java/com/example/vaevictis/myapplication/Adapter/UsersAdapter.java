package com.example.vaevictis.myapplication.Adapter;

/**
 * Created by vaevictis on 14.12.17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vaevictis.myapplication.APIProvider.APIProvider;
import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        CircleImageView imageView = convertView.findViewById(R.id.tv_avatar);

        if(user.getAvatar() != null) {
            Picasso.with(getContext()).load(APIProvider.BASE_URL  + user.getAvatar())
                    .placeholder(R.drawable.avatar)
                    .into(imageView);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tv_email);
        TextView tvHome = convertView.findViewById(R.id.tv_role);
        TextView tvDist = convertView.findViewById(R.id.tv_dist);

        // Populate the data into the template view using the data object
        tvName.setText(user.getEmail());
        tvHome.setText(user.getRole());
        tvDist.setText(" - " +user.getDist() + " km from you");

        // Return the completed view to render on screen
        return convertView;
    }
}
