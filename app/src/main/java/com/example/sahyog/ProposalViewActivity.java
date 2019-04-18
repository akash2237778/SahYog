package com.example.sahyog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

   }
    Intent mapDirIntent;
    Intent intent;

    public void onClickLocation(View view){
        Toast.makeText(ProposalViewActivity.this, intent.getStringExtra("userNames"), Toast.LENGTH_SHORT).show();
        startActivity(mapDirIntent);
         }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_view);



        chatActIntent=new Intent(getApplicationContext(),ChatActivity.class);

        intent = getIntent();
        mapDirIntent = new Intent(getApplicationContext(),mapDirectionActivity.class);
        objid = intent.getStringExtra("ObjectId");

        chatActIntent.putExtra("userName", intent.getStringExtra("userNames"));
        ProviderUserName = String.valueOf(ParseUser.getCurrentUser().getUsername());
       //Double lat = intent.getDoubleExtra("lat",30.11);
        //Double long1 = intent.getDoubleExtra("long",30.11);
        //Log.i("ObjectID " , objid );
        mapDirIntent.putExtra("userName", intent.getStringExtra("userNames"));
        mapDirIntent.putExtra("lat", intent.getDoubleExtra("lat",30.11));
        mapDirIntent.putExtra("long", intent.getDoubleExtra("long",30.11));

    }
}
