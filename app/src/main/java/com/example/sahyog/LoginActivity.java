package com.example.sahyog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    EditText logInUsrNmeText ;
    EditText logInPassText;
    String loginUsrNme;
    String loginPass;
    Intent MainNavDrawerIntent;

    public void OnClickLogin(View view){
        loginUsrNme = logInUsrNmeText.getText().toString();
        loginPass = logInPassText.getText().toString();

        ParseUser.logInInBackground(loginUsrNme, loginPass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null){
                    Log.i("logIn ", "Successful");
                    startActivity(MainNavDrawerIntent);
                }else{
                    Log.i("logIn ", "unSuccessful" + e.getMessage() );
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInUsrNmeText = (EditText) findViewById(R.id.loginUsrNmeText);
        logInPassText = (EditText) findViewById(R.id.logInPassText);
        MainNavDrawerIntent = new Intent(getApplicationContext(),MainActNavDrawer.class);

    }
}
