package com.example.vaevictis.myapplication.views.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.example.vaevictis.myapplication.APIProvider.APIProvider;
import com.example.vaevictis.myapplication.R;
import com.example.vaevictis.myapplication.Utils;
import com.example.vaevictis.myapplication.controllers.UserController;
import com.example.vaevictis.myapplication.models.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class HelpFragment extends Fragment implements OnMapReadyCallback, RoutingListener {
    private View myView;
    private UserController userController;
    private boolean isDestroyed = false;
    public SupportMapFragment mapFrag;
    static public GoogleMap map = null;
    ArrayList<Polyline> polylines = new ArrayList<>();
    private static final int[] COLORS = new int[]{R.color.primary_dark,R.color.primary,R.color.primary_light,R.color.accent,R.color.primary_dark_material_light};

    static Marker myMaker;
    static public Marker toHelpMaker;

    private CounterFab stopHelpButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_help, container, false);

        mapFrag = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapsfragment);
        mapFrag.getMapAsync(this);

        userController = new UserController(getActivity());
        stopHelpButton = myView.findViewById(R.id.stop_help);

        addListenerOnButton();

        return myView;
    }
    public  void addListenerOnButton(){
        stopHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userController.willStopHelp();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.clear();
        UserController.currentMap = map;

        userController.updateMap();

        userController.displayRoutes(this);
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        System.out.println("onRoutingFailure");
        System.out.println(e.getMessage());

    }

    @Override
    public void onRoutingStart() {
        System.out.println("onRoutingStart");

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        System.out.println("onRoutingSuccess");

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = map.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getActivity().getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }
//        marker = myMaker;
//        getAndDrawCustomMaker(UserController.user, APIProvider.BASE_URL + "images/" + UserController.user.get_id() + "-green.png");
//        marker = toHelpMaker;
        userController.updateMap();

    }

    static public void getAndDrawCustomMaker(final User user, String url, Context context){

        final int height = 100;
        final int width = 100;
        Picasso.with(context).load(APIProvider.BASE_URL + "images/" + user.get_id() + url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                        bitmap = Utils.getCircular(bitmap, bitmap.getWidth()/2);
//                        bitmap = Utils.addColorBorder(bitmap, 8, Color.BLACK);
                        if(toHelpMaker != null) {
                            toHelpMaker.remove();
                        }
                        toHelpMaker = map.addMarker(new MarkerOptions().position(user.getLatLng()).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        System.out.println("onBitmapFailed");
                        if(toHelpMaker != null) {
                            toHelpMaker.remove();
                        }
                       toHelpMaker = map.addMarker(new MarkerOptions().position(user.getLatLng()));

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        System.out.println("onPrepareLoad");
                        if(toHelpMaker != null) {
                            toHelpMaker.remove();
                        }
                        toHelpMaker = map.addMarker(new MarkerOptions().position(user.getLatLng()));

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }

    @Override
    public void onRoutingCancelled() {
        System.out.println("onRoutingCancelled");
    }

}
