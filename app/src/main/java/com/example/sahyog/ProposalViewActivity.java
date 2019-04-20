package com.example.sahyog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProposalViewActivity extends AppCompatActivity {

    Intent chatActIntent;
    String objid;
    String ProviderUserName;
    int Status;
    ImageView statusImageView;
    TextView statusTextView;
    Button btnCnfrm;
    String activeUserName;
    Intent LiveLocationIntent;
    Button LiveLocationBTN;
    Button completeServiceBTN;
    Button serviceLocationBTN;
    TextView NameTextView;
    TextView PhoneTextView;
    TextView serviceTextView;
    TextView TimeTextView;
    TextView DateTextView;

    public void OnclickLivelocation(View view){
        startActivity(LiveLocationIntent);
    }


    public void StatusTextViewSetter(int a){
        String statusString;
        statusString = "NULL";

        if(a==0){
            statusString = "Status : Unoccupied";
            statusImageView.setImageResource(R.drawable.bulebutton);
        }
        else if(a==1){
            statusString = "Status : Confirmed";
            statusImageView.setImageResource(R.drawable.yellowbutton);
            btnCnfrm.setVisibility(View.INVISIBLE);
            LiveLocationBTN.setVisibility(View.VISIBLE);
            if(ProviderUserName.equals(ParseUser.getCurrentUser().getUsername())){
            PhoneTextView.setVisibility(View.VISIBLE);}
        }else if(a==2){
            statusString = "Status : Completed";
            statusImageView.setImageResource(R.drawable.greenbutton);
            btnCnfrm.setVisibility(View.INVISIBLE);
            //completeServiceBTN.setVisibility(View.INVISIBLE);
        }else{
            statusString = "Status : ERROR";
        }

        statusTextView.setText(statusString);

    }


    public void callChat(View view)
    {
        startActivity(chatActIntent);
    }

   public void callConfirm(View view) {



        ParseQuery<ParseObject> query=ParseQuery.getQuery("ServiceProvider");
        query.getInBackground(objid,new GetCallback<ParseObject>(){
                                   @Override
                                   public void done(ParseObject object, ParseException e) {
                                       if(e==null && object!=null){
                                           object.put("ProviderUserName",ProviderUserName );
                                           object.put("ConfirmStatus",1);
                                           object.saveInBackground();
                                       }
                                   }

    });
       btnCnfrm.setAlpha(0);
       statusTextView.setText("Status : Confirmed");
       statusImageView.setImageResource(R.drawable.yellowbutton);

       if(ProviderUserName.equals(ParseUser.getCurrentUser().getUsername())){
           PhoneTextView.setVisibility(View.VISIBLE);}
   }
    public void OnclickCompleteService(View view){
        ParseQuery<ParseObject> query=ParseQuery.getQuery("ServiceProvider");
        query.getInBackground(objid,new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null && object != null) {
                            object.put("ProviderUserName", ProviderUserName);
                            object.put("ConfirmStatus", 2);
                            object.saveInBackground();

                        }
                    }
        });
                completeServiceBTN.setAlpha(0);
        statusTextView.setText("Status : Completed");
        statusImageView.setImageResource(R.drawable.greenbutton);
    }


            Intent mapDirIntent;
            Intent intent;

            public void onClickLocation(View view) {
                startService(new Intent( ProposalViewActivity.this,LiveLocationActivity.class));
                startActivity(mapDirIntent);

            }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_view);

        LiveLocationBTN = findViewById(R.id.LiveLocationButton);
        completeServiceBTN = findViewById(R.id.CompleteServiceButton);
        serviceLocationBTN = findViewById(R.id.ServicelocationBTN);
        statusImageView = findViewById(R.id.statusImageViewPA);
        statusTextView = findViewById(R.id.statusViewPA);
        btnCnfrm = findViewById(R.id.btnConfirm);
        NameTextView = findViewById(R.id.NameTextview);
        PhoneTextView = findViewById(R.id.phoneTextview);
        serviceTextView = findViewById(R.id.ServiceTextView);
        DateTextView = findViewById(R.id.DateView);
        TimeTextView = findViewById(R.id.TimeView);

        chatActIntent=new Intent(getApplicationContext(),ChatActivity.class);
        LiveLocationIntent = new Intent(getApplicationContext() , LiveLocationActivity.class);

        intent = getIntent();
        mapDirIntent = new Intent(getApplicationContext(),mapDirectionActivity.class);
        objid = intent.getStringExtra("ObjectId");
        Status = intent.getIntExtra("Status" , 0);

        chatActIntent.putExtra("userName", intent.getStringExtra("userNames"));
        ProviderUserName = String.valueOf(ParseUser.getCurrentUser().getUsername());
        activeUserName = intent.getStringExtra("userNames");
       //Double lat = intent.getDoubleExtra("lat",30.11);
        //Double long1 = intent.getDoubleExtra("long",30.11);
        //Log.i("ObjectID " , objid );
        mapDirIntent.putExtra("userName", intent.getStringExtra("userNames"));
        mapDirIntent.putExtra("lat", intent.getDoubleExtra("lat",30.11));
        mapDirIntent.putExtra("long", intent.getDoubleExtra("long",30.11));

        StatusTextViewSetter(Status);
        Log.i("check " , "ParseUser" + activeUserName);

        if(ProviderUserName.equals(activeUserName)) {
            btnCnfrm.setVisibility(View.INVISIBLE);
            serviceLocationBTN.setVisibility(View.INVISIBLE);
            if (Status != 2) {
                completeServiceBTN.setVisibility(View.VISIBLE);
            }
        }

//initialCommentForBranchAkash_Saini




        ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("ServiceProvider");
       queryForUsername.whereEqualTo("username" , activeUserName );
        queryForUsername.setLimit(1);
        queryForUsername.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size()>0){
                        int i=0;
                        for(ParseObject UserInfo : objects){
                            String phoneNumber = UserInfo.getString("phone");
                            String Services = UserInfo.getString("service");
                            String time = UserInfo.getString("Time");
                            String date = UserInfo.getString("Date");

                            NameTextView.setText(activeUserName);
                            PhoneTextView.setText(phoneNumber);
                            serviceTextView.setText(Services);
                            DateTextView.setText(time);
                            TimeTextView.setText(date);
                        }

                    }
                }
            }
        });

    }
}
