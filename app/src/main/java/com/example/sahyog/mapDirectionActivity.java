package com.example.sahyog;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class mapDirectionActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String userName;
    Double Latitude;
    Number Longitude;
    Intent mapDirIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_direction);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mapDirIntent = getIntent();
        userName = mapDirIntent.getStringExtra("userName");



        ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("ServiceProvider");
       queryForUsername.whereEqualTo("username",userName);
        queryForUsername.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size()>0){
                        for(ParseObject UserInfo : objects){

                            Latitude = (Double) UserInfo.getNumber("LocationLAT");
                            Longitude = UserInfo.getNumber("LocationLONG");

                        }
                     }
                    }
                }
            });


        Log.i("lat+long " ,userName + String.valueOf(Latitude) + String.valueOf(Longitude));

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(33, 71.3);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Service Receiver"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
