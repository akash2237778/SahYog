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
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProvideService extends AppCompatActivity {
    EditText ET_service;
    EditText ET_maxweight;
    Intent mapActivityIntent;
    EditText DateView;
    EditText TimeView;
    EditText PhoneNumerEditText;




    public void getProLoc(View view) {

        mapActivityIntent.putExtra("pro_service",ET_service.getText().toString());
        //mapActivityIntent.putExtra("pro_range",Double.parseDouble(ET_range.getText().toString()));
        mapActivityIntent.putExtra("pro_maxweight",Double.parseDouble(ET_maxweight.getText().toString()));
        mapActivityIntent.putExtra("Time",TimeView.getText().toString());
       mapActivityIntent.putExtra("Date",DateView.getText().toString());
        mapActivityIntent.putExtra("phoneNumber" , PhoneNumerEditText.getText().toString());
        startActivity(mapActivityIntent);


    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_service);
setTitle("Fill Required Service Details");
        mapActivityIntent = new Intent(getApplicationContext(),MapsActivity.class);





         ET_service=findViewById(R.id.txtservice);
        PhoneNumerEditText = findViewById(R.id.PhoneNumerEditText);

//<<<<<<< akansha

//=======

//>>>>>>> Dev_akash
        // ET_range=findViewById(R.id.txtrange);
         ET_maxweight=findViewById(R.id.txtweight);
         DateView = findViewById(R.id.DateView);
         TimeView = findViewById(R.id.TimeView);





    }
}
