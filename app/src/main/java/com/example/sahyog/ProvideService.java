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
    Intent mapActivityIntent;

//<<<<<<< akansha
    String pro_username,pro_service, pro_peraddress, pro_curloc;
    double pro_range,pro_maxweight;
//=======
    LocationManager locationManager;
    LocationListener locationListener;
    TextView textViewAddress;
    String address = "Last Location";

//>>>>>>> Dev_akash



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



    public void getProLoc(View view) {

        mapActivityIntent.putExtra("pro_service",ET_service.getText().toString());
        mapActivityIntent.putExtra("pro_range",Double.parseDouble(ET_range.getText().toString()));
        mapActivityIntent.putExtra("pro_maxweight",Double.parseDouble(ET_maxweight.getText().toString()));

/*
        pro_service = ET_service.getText().toString();
       // pro_peraddress = ET_peraddress.getText().toString();
        pro_curloc = ET_curloc.getText().toString();
        pro_range = Double.parseDouble(ET_range.getText().toString());
        pro_maxweight = Double.parseDouble(ET_maxweight.getText().toString());

        ParseObject provider= new ParseObject("ServiceProvider");

        pro_username= String.valueOf(ParseUser.getCurrentUser().getUsername());

        provider.put("username",pro_username);
        provider.put("service",pro_service);
       // provider.put("PerAddress",pro_peraddress);
        //provider.put("CurLocation",pro_curloc);
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
*/
        startActivity(mapActivityIntent);


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_service);

        mapActivityIntent = new Intent(getApplicationContext(),MapsActivity.class);





         ET_service=findViewById(R.id.txtservice);
         ET_peraddress=findViewById(R.id.txtadd);
//<<<<<<< akansha
   //      ET_curloc=findViewById(R.id.txtcurlocation);
//=======
         ET_curloc=findViewById(R.id.txtcurlocaion);
//>>>>>>> Dev_akash
         ET_range=findViewById(R.id.txtrange);
         ET_maxweight=findViewById(R.id.txtweight);

        textViewAddress = (TextView)findViewById(R.id.txtcurlocaion) ;





    }
}
