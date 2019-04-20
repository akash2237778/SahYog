package com.example.sahyog;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LiveLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMap mMap2;
    ParseObject livetrackerObj;

    Location locationSet;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng latLong;
    LatLng latLong2;
    String addressLine;
    String addressLine2beStored;
    LatLng latLngToBeStored;
    String ObjIdParseServer;
    final Double[] Latitude = new Double[1];
    final Double[] Longitude = new Double[1];
    String RecipUsrName;


    public void GetRecipientName(String name){
        RecipUsrName = name;

    }

    public void blueMarkOnMap(Double Lat , Double Long){


        latLong2 = new LatLng(Lat, Long);

        //mMap2.clear();
        mMap2.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(latLong2));
       // mMap2.moveCamera(CameraUpdateFactory.newLatLng(latLong));

        Log.i("info", Lat.toString());
        /*  Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(Lat,Long,1);
            addressLine = addressList.get(0).getAddressLine(0);
            //ToastMaker(addressLine);


        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public void InfoUpdatetoServer(Location locationToSet) {

        livetrackerObj.put("CurUsrName", ParseUser.getCurrentUser().getUsername());
        livetrackerObj.put("RecipientUsrName", RecipUsrName);
        livetrackerObj.put("LAT", locationToSet.getLatitude());
        livetrackerObj.put("LONG", locationToSet.getLongitude());
        ObjIdParseServer =  livetrackerObj.getObjectId();

        livetrackerObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e==null ){
                   // Toast.makeText(LiveLocationActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    Log.i("Submit ","Successful");
                }else{
                    Log.i("Submit ","unSuccessful :" + e.getMessage());
                }
            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        livetrackerObj = new ParseObject("LiveLocation");


        ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("ServiceProvider");
        queryForUsername.whereEqualTo("username" , ParseUser.getCurrentUser().getUsername() );
        queryForUsername.whereEqualTo("ConfirmStatus", 1 );
        queryForUsername.orderByDescending("updatedAt");
        queryForUsername.setLimit(1);
        queryForUsername.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        int i = 0;
                        for (ParseObject UserInfo : objects) {
                            String RUsrName = UserInfo.getString("ProviderUserName");
                            GetRecipientName(RUsrName);


                        }
                    }
                }
            }

        });



        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                UpdateLocationChangeInfo(location);
                Log.i("info :", "location");
                locationSet = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

    }

    public void ToastMaker(String string){
        Toast.makeText(LiveLocationActivity.this, string , Toast.LENGTH_SHORT).show();
    }


    public void UpdateLocationChangeInfoFromServer() {


        ParseQuery<ParseObject> queryForLocation = ParseQuery.getQuery("LiveLocation");
        queryForLocation.whereEqualTo("CurUsrName", RecipUsrName);
        queryForLocation.whereEqualTo("RecipientUsrName", ParseUser.getCurrentUser().getUsername());
        queryForLocation.orderByDescending("updatedAt");
        queryForLocation.setLimit(1);
        //queryForLocation.whereEqualTo("objectId",ObjIdParseServer);
        queryForLocation.findInBackground(new FindCallback<ParseObject>() {
           @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {


                    if (objects.size() > 0) {
                        int i = 0;
                        for (ParseObject UserInfo : objects) {
                            Double LAT = (Double) UserInfo.getNumber("LAT");
                            Double LONG = (Double) UserInfo.getNumber("LONG");
                            Latitude[0] = LAT;
                            Longitude[0] = LONG;

                            Log.i("Recipient" , UserInfo.getString("RecipientUsrName"));

                            blueMarkOnMap(Latitude[0] , Longitude[0]);

                            i++;


                        }
                    }
                }
            }



        });

        Latitude[0] = 30.454888;
        Log.i("Latitude" , Latitude[0].toString());

  }

    public void UpdateLocationChangeInfo(Location location) {
        latLong = new LatLng(location.getLatitude(), location.getLongitude());
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));

        Log.i("info", location.toString());
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            addressLine = addressList.get(0).getAddressLine(0);
          // ToastMaker(addressLine);
            InfoUpdatetoServer(location);


            UpdateLocationChangeInfoFromServer();

        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLong));


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
        mMap2 = googleMap;

        // Add a marker in Sydney and move the camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 5000 , 0 ,locationListener );
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng mylocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(mylocation).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15 ));
            Toast.makeText(LiveLocationActivity.this, "Updating Location........", Toast.LENGTH_LONG).show();

        }

    }
}
