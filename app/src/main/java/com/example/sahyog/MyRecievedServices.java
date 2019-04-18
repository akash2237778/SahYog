
package com.example.sahyog;

        import android.content.Intent;
        import android.location.Address;
        import android.location.Geocoder;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
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



public class MyRecievedServices extends AppCompatActivity {
    RecyclerView recyclerView;
    Intent proposalActivityIntent;
    Intent mapDirectionIntent;
    Double Latitude;
    Double Longitude;
    Double[] LatitudeArr;
    Double[] LongitudeArr;
    int SIZE;
    int ConfrmStatus;

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
    public int StatusImageViewSetter(int a){
        int statusString;
        statusString = 0;

        if(a==0){
            statusString = -700015;

        }
        else if(a==1){
            statusString = -700022;
        }else if(a==2){
            statusString = -700089;
        }else{
            statusString = -700089;
        }

        return statusString;

    }


    public String GeocoderProg(Double Latitude , Double Longitude){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(Latitude,Longitude,1);
            addressLine2beStored = addressList.get(0).getAddressLine(0);
            Toast.makeText(MyRecievedServices.this, addressLine2beStored , Toast.LENGTH_SHORT).show();
            return  addressLine2beStored;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error while geocoding location";
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recieved_services);

        proposalActivityIntent = new Intent(getApplicationContext(), ProposalViewActivity.class);
        // mapDirectionIntent = new Intent(getApplicationContext(),mapDirectionActivity.class);

        ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("ServiceProvider");
        queryForUsername.whereEqualTo("username" , ParseUser.getCurrentUser().getUsername() );
        queryForUsername.orderByDescending("createdAt");
        queryForUsername.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
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
                            ImageStatusText[i] = StatusImageViewSetter(ConfrmStatus);
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

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        proposalActivityIntent.putExtra("userNames", names[position]);
                        proposalActivityIntent.putExtra("lat", LatitudeArr[position]);
                        proposalActivityIntent.putExtra("long", LongitudeArr[position]);
                        proposalActivityIntent.putExtra("ObjectId", ObjectId[position]);

                        //mapDirectionIntent.putExtra("userNames",names[position]);
                        startActivity(proposalActivityIntent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }

}
