package com.example.sahyog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class mapDirectionActivity extends FragmentActivity implements OnMapReadyCallback {

   // private GoogleMap mMap2;
    private GoogleMap mMap;
    String userName;
    Double Latitude;
    Double Longitude;
    Intent mapDirIntent;
    LatLng latLong;
    String addressLine;
    String addressLine2beStored;
    LatLng latLngToBeStored;
    LocationManager locationManager;
    LocationListener locationListener;
    Location locationSet;
    Button buttonAddPos;


    public void AddPosition(View view){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+latLong.latitude +"," + latLong.longitude +"&daddr="+Latitude+","+Longitude));
        startActivity(intent);
    }


    public void ToastMaker(String string){
        Toast.makeText(mapDirectionActivity.this, string , Toast.LENGTH_SHORT).show();
    }

    public void UpdateLocationChangeInfo(Location location) {
         buttonAddPos.setVisibility(View.VISIBLE);

        latLong = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(latLong));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
       /*
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


                mMap.addMarker(new MarkerOptions().position((latLng)));
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                    addressLine2beStored = addressList.get(0).getAddressLine(0);
                    ToastMaker(addressLine2beStored);
                    latLngToBeStored = latLng;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });
        */
        Log.i("info", location.toString());
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            addressLine = addressList.get(0).getAddressLine(0);
            ToastMaker(addressLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, locationListener);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_direction);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buttonAddPos = findViewById(R.id.buttonAddPosition2);

        mapDirIntent = getIntent();
        userName = mapDirIntent.getStringExtra("userName");
        Latitude = mapDirIntent.getDoubleExtra("lat" , 30.40496952);
        Longitude = mapDirIntent.getDoubleExtra("long" , 77.93542722);



     /*   ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("ServiceProvider");
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

*/
        Log.i("lat+long " ,userName + String.valueOf(Latitude) + String.valueOf(Longitude));

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
        LatLng serviceReciverLOC = new LatLng(Latitude, Longitude);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(serviceReciverLOC).title("Service Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(serviceReciverLOC,15));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 0 , 1000 ,locationListener );
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        }
    }
}
