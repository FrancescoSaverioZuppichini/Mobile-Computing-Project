package com.example.vaevictis.myapplication.views.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vaevictis.myapplication.Adapter.UsersAdapter;
import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.controllers.UserController;


public class UsersFragment extends ListFragment implements AdapterView.OnItemClickListener {
    View myView;
    public static UsersAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_users, container, false);

        return myView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new UsersAdapter(getActivity(), UserController.usersThatHelps);
        ListView listView = myView.findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

}