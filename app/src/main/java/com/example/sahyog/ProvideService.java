package com.example.sahyog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ProvideService extends AppCompatActivity {
    EditText ET_service;
    EditText ET_peraddress;
    EditText ET_curloc;
    EditText ET_range;
    EditText ET_maxweight;
    String pro_username,pro_phone,pro_service, pro_peraddress, pro_curloc, pro_range,pro_maxweight;




    public void getProLoc(View view)
    {


        ET_curloc.setText("");
    }

    public void proFormSubmit(View view)
    {
        pro_service = ET_service.getText().toString();
        pro_peraddress = ET_peraddress.getText().toString();
        pro_curloc = ET_curloc.getText().toString();
        pro_range = ET_range.getText().toString();
        pro_maxweight = ET_maxweight.getText().toString();
        ParseObject provider= new ParseObject("ServiceProvider");

        pro_username= String.valueOf(ParseUser.getCurrentUser().getUsername());

        provider.put("username","pro_username");
        provider.put("service","pro_service");
        provider.put("PerAddress","pro_peraddress");
        provider.put("CurLocation","pro_curloc");
        provider.put("ServiceRange","pro_range");
        provider.put("MaxWeight","pro_maxweight");
        provider.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e==null ){
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


         ET_service=findViewById(R.id.txtservice);
         ET_peraddress=findViewById(R.id.txtadd);
         ET_curloc=findViewById(R.id.txtcurlocaion);
         ET_range=findViewById(R.id.txtrange);
         ET_maxweight=findViewById(R.id.txtweight);


    }
}
