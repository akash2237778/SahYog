package com.example.sahyog;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActNavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    Intent proposalActivityIntent;
    Intent mapDirectionIntent;
    Double Latitude;
    Double Longitude;
    Double[] LatitudeArr;
    Double[] LongitudeArr;
    int SIZE;
    Intent intentMyProvideServices;
    Intent intentMyRecievedServices;
    int ConfrmStatus;
    SwipeRefreshLayout pullToRefresh;



ArrayList<String> arrayListToStoreUserData = new ArrayList<>();
//ArrayAdapter arrayAdapterForStoreUserData;

    String[] names;
    String[] userServiceArr;
    String[] userCurAddressArr;
    String[] ObjectId;
    String[] StatusText;
    int[] ImageStatusText;


    String addressLine2beStored;


    public String StatusTextViewSetter(int a){
        String statusString;
        statusString = "NULL";

        if(a==0){
            statusString = "Status : Unoccupied";
        }
        else if(a==1){
            statusString = "Status : Confirmed";
        }else if(a==2){
            statusString = "Status : Completed";
        }else{
            statusString = "Status : ERROR";
        }

        return statusString;

    }



    public String GeocoderProg(Double Latitude , Double Longitude){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(Latitude,Longitude,1);
            addressLine2beStored = addressList.get(0).getAddressLine(0);
          //  Toast.makeText(MainActNavDrawer.this, addressLine2beStored , Toast.LENGTH_SHORT).show();
            return  addressLine2beStored;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error while geocoding location";
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_act_nav_drawer);
        setTitle("Required Services");
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh2);


            intentMyProvideServices = new Intent(getApplicationContext(),myProvideServices.class);
            intentMyRecievedServices = new Intent(getApplicationContext(),MyRecievedServices.class);

        proposalActivityIntent = new Intent(getApplicationContext(),ProposalViewActivity.class);
       // mapDirectionIntent = new Intent(getApplicationContext(),mapDirectionActivity.class);

        ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("ServiceProvider");
        //queryForUsername.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername() );
        queryForUsername.orderByDescending("createdAt");
        queryForUsername.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    SIZE = objects.size();
                    LatitudeArr = new Double[SIZE];
                    LongitudeArr = new Double[SIZE];
                    names = new String[SIZE];
                    userServiceArr = new String[SIZE];
                    userCurAddressArr = new String[SIZE];
                    ObjectId = new String[SIZE];
                    StatusText = new String[SIZE];
                    ImageStatusText = new int[SIZE];


                    if(objects.size()>0){
                        int i=0;
                        for(ParseObject UserInfo : objects){
                            String userName = UserInfo.getString("username");
                            String userService = UserInfo.getString("service");
                            ConfrmStatus = UserInfo.getInt("ConfirmStatus");
                            Latitude = UserInfo.getDouble("LocationLAT");
                            Longitude = UserInfo.getDouble("LocationLONG");
                            String ObjectID = UserInfo.getObjectId();
                            Log.i("ParseInfo :" , userService + String.valueOf(Latitude));
                            StatusText[i] = StatusTextViewSetter(ConfrmStatus);
                            ImageStatusText[i] = ConfrmStatus;
                            userServiceArr[i] = userService;
                            userCurAddressArr[i] = GeocoderProg(Latitude,Longitude);
                            LatitudeArr[i]=Latitude;
                            LongitudeArr[i] = Longitude;
                            ObjectId[i] = ObjectID;

                           names[i] = userName;

                            i++;
                            recyclerView.setAdapter(new AdapterProgram(names , userServiceArr , userCurAddressArr , StatusText, ImageStatusText));

                        }

                    }
                }
            }
        });




        final Intent intent_provide = new Intent(this, ProvideService.class);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                         proposalActivityIntent.putExtra("userNames" , names[position]);
                         proposalActivityIntent.putExtra("lat" ,LatitudeArr[position] );
                         proposalActivityIntent.putExtra("long",LongitudeArr[position]);
                        proposalActivityIntent.putExtra("ObjectId", ObjectId[position]);
                        proposalActivityIntent.putExtra("Status" , ImageStatusText[position]);
                         //mapDirectionIntent.putExtra("userNames",names[position]);
                        startActivity(proposalActivityIntent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("ServiceProvider");
                //queryForUsername.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername() );
                queryForUsername.orderByDescending("createdAt");
                queryForUsername.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            SIZE = objects.size();
                            LatitudeArr = new Double[SIZE];
                            LongitudeArr = new Double[SIZE];
                            names = new String[SIZE];
                            userServiceArr = new String[SIZE];
                            userCurAddressArr = new String[SIZE];
                            ObjectId = new String[SIZE];
                            StatusText = new String[SIZE];
                            ImageStatusText = new int[SIZE];


                            if(objects.size()>0){
                                int i=0;
                                for(ParseObject UserInfo : objects){
                                    String userName = UserInfo.getString("username");
                                    String userService = UserInfo.getString("service");
                                    ConfrmStatus = UserInfo.getInt("ConfirmStatus");
                                    Latitude = UserInfo.getDouble("LocationLAT");
                                    Longitude = UserInfo.getDouble("LocationLONG");
                                    String ObjectID = UserInfo.getObjectId();
                                    Log.i("ParseInfo :" , userService + String.valueOf(Latitude));
                                    StatusText[i] = StatusTextViewSetter(ConfrmStatus);
                                    ImageStatusText[i] = ConfrmStatus;
                                    userServiceArr[i] = userService;
                                    userCurAddressArr[i] = GeocoderProg(Latitude,Longitude);
                                    LatitudeArr[i]=Latitude;
                                    LongitudeArr[i] = Longitude;
                                    ObjectId[i] = ObjectID;

                                    names[i] = userName;

                                    i++;
                                    recyclerView.setAdapter(new AdapterProgram(names , userServiceArr , userCurAddressArr , StatusText, ImageStatusText));

                                }

                            }
                        }
                    }
                });

                pullToRefresh.setRefreshing(false);

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent_provide);
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_act_nav_drawer, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuitem_provide) {
            startActivity(intentMyProvideServices);

        }  else if (id == R.id.menuitem_recieve) {
            startActivity(intentMyRecievedServices);

        }
        else if (id == R.id.nav_share) {
         //  Toast.makeText(MainActNavDrawer.this, "hiii", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
