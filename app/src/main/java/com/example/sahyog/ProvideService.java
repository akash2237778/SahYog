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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
//<<<<<<< akansha
//=======
import android.widget.Toast;
//>>>>>>> Dev_akash

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ProvideService extends AppCompatActivity {
    EditText ET_service;
    EditText ET_peraddress;
    TextView ET_curloc;
    EditText ET_range;
    EditText ET_maxweight;

//<<<<<<< akansha
    String pro_username,pro_service, pro_peraddress, pro_curloc;
    double pro_range,pro_maxweight;
//=======
    LocationManager locationManager;
    LocationListener locationListener;
    TextView textViewAddress;
    String address = "Wait For A While .....";
//>>>>>>> Dev_akash



    DatabaseReference databaseReferenceForPFrom;
    FireBaseToStoreProvideServiceForm objectToSendToJavaClass;



    public void setTextViewInfo( TextView textView ,String toBeTyped){
        textView.setText(toBeTyped);
    }

    public void updateLocationInfo(Location location){
        Log.i("info", location.toString());

       // Toast.makeText(ProvideService.this, address, Toast.LENGTH_SHORT).show();
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listOfGeocoder  = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            address = listOfGeocoder.get(0).getAddressLine(0);
            Log.i("info " , " geocoder successful " + address);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void startListening(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 12, 10, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }
    public void getProLoc(View view) {
        setTextViewInfo(textViewAddress,address);


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_service);

       // FirebaseApp.initializeApp(this);
        databaseReferenceForPFrom = FirebaseDatabase.getInstance().getReference();
        databaseReferenceForPFrom.child("ProvideServiceDatabase");


        ET_service=findViewById(R.id.txtservice);
         ET_peraddress=findViewById(R.id.txtadd);
//<<<<<<< akansha
   //      ET_curloc=findViewById(R.id.txtcurlocation);
//=======
         ET_curloc=findViewById(R.id.txtcurlocaion);
//>>>>>>> Dev_akash
         ET_range=findViewById(R.id.txtrange);
         ET_maxweight=findViewById(R.id.txtweight);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        textViewAddress = (TextView)findViewById(R.id.txtcurlocaion) ;


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updateLocationInfo(location);


                // setTextViewInfo(textViewLatitude, String.valueOf(location.getLatitude()));
               // setTextViewInfo(textViewLongitude,String.valueOf(location.getLongitude()));
               // setTextViewInfo(textViewSpeed,String.valueOf(location.getSpeed())+" meters/sec");
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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {

           // startListening();
           // Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
           // updateLocationInfo(lastLocation);

        }


    }
    public void proFormSubmit(View view)
    {
        pro_service = ET_service.getText().toString();
        pro_peraddress = ET_peraddress.getText().toString();
        pro_curloc = ET_curloc.getText().toString();
        pro_range = Double.parseDouble(ET_range.getText().toString());
        pro_maxweight = Double.parseDouble(ET_maxweight.getText().toString());

        ParseObject provider= new ParseObject("ServiceProvider");

        pro_username= String.valueOf(ParseUser.getCurrentUser().getUsername());

        objectToSendToJavaClass.setPro_service("Service");
        objectToSendToJavaClass.setPro_username(pro_username);
        objectToSendToJavaClass.setPro_peraddress(pro_peraddress);
        objectToSendToJavaClass.setPro_curloc(pro_curloc);
        objectToSendToJavaClass.setPro_range(pro_range);
        objectToSendToJavaClass.setPro_maxweight(pro_maxweight);

        databaseReferenceForPFrom.push().setValue(objectToSendToJavaClass);





        provider.put("username",pro_username);
        provider.put("service",pro_service);
        provider.put("PerAddress",pro_peraddress);
        provider.put("CurLocation",pro_curloc);
        provider.put("ServiceRange",pro_range);
//<<<<<<< akansha
        provider.put("MaximumWeight",pro_maxweight);
        //
//  provider.put("MaxWeight",pro_maxweight);
//>>>>>>> Dev_akash
        provider.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e==null ){
                    Toast.makeText(ProvideService.this, "Successful", Toast.LENGTH_SHORT).show();
                    Log.i("Submit ","Successful");
                }else{
                    Log.i("Submit ","unSuccessful :" + e.getMessage());
                }
            }
        });
    }

}
