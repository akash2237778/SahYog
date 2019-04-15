package com.example.sahyog;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseServerDetails extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("697c212c3a04ab63611a1a29261feec124a4c511")
                // if defined
                .clientKey("6a738f5d8d76b60e42fba96a3f315aec6e708821")
                .server("http://52.66.202.216:80/parse")
                .build()
        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}