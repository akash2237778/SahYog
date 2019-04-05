package com.example.sahyog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser user = new ParseUser();
        user.setUsername("Akash");
        user.setPassword("123456");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if( e==null ){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "unSuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
