package com.example.sahyog;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessagesAct extends AppCompatActivity {

    RecyclerView recyclerView;
    Intent chatActivityIntent;
    int SIZE;
    //int SIZE_user;
    SwipeRefreshLayout pullToRefresh;


    ArrayList<String> arrayListToStoreUserData = new ArrayList<>();

    String[] sendernames;
    //String[] usernames;
    String[] finalsendernames;
    //String[] recnames;
    String[] ObjectId;
    //String[] dateArr;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        setTitle("Your Messages");

        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh2);
        chatActivityIntent = new Intent(getApplicationContext(), ChatActivity.class);


         ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("Message");
         queryForUsername.whereEqualTo("recipient" , ParseUser.getCurrentUser().getUsername() );
         queryForUsername.orderByDescending("createdAt");
         queryForUsername.findInBackground(new FindCallback<ParseObject>() {
             @Override
             public void done(List<ParseObject> objects, ParseException e) {
                 if (e == null) {
                     SIZE = objects.size();
                     sendernames = new String[SIZE];
                     ObjectId = new String[SIZE];


                     //    dateArr=new String[SIZE];

                     int i=0;
                     if(objects.size()>0){

                         for(ParseObject UserInfo : objects){
                             String senderName = UserInfo.getString("sender");
                             String ObjectID = UserInfo.getObjectId();
                             Date msgDate=UserInfo.getDate("createdAt");
                             String strDate=msgDate+"";
                             ObjectId[i] = ObjectID;
                             //          dateArr[i]=strDate;

                             sendernames[i] = senderName;

                         }
                         int k,j=0,count=0;
                         for(k=0;k<SIZE;k++)
                         {
                             if(sendernames[k]==sendernames[k+1])
                             {k++;}
                             else
                             {count++;}
                         }

                         j=0;finalsendernames=new String[count];
                         for(k=0;k<SIZE;k++)
                         {
                             if(sendernames[k]==sendernames[k+1])
                             {k++;}
                             else
                             {finalsendernames[j]=sendernames[k];j++;
                                 recyclerView.setAdapter(new AdapterMsg(finalsendernames ));
                             }
                         }


                     }
                 }
             }
         });


        /*int i,j=0,count=0;
        for(i=0;i<SIZE;i++)
        {
            if(sendernames[i]==sendernames[i+1])
            {i++;}
            else
            {count++;}
        }

         j=0;finalsendernames=new String[count];
         for(i=0;i<SIZE;i++)
         {
             if(sendernames[i]==sendernames[i+1])
             {i++;}
             else
             {finalsendernames[j]=sendernames[i];j++;
                 recyclerView.setAdapter(new AdapterMsg(finalsendernames ));
             }
         }
*/

         //final Intent intent_provide = new Intent(this, ProvideService.class);

        recyclerView = (RecyclerView) findViewById(R.id.MsgRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        chatActivityIntent.putExtra("userName", finalsendernames[position]);
                       //chatActivityIntent.putExtra("recipient", recnames[position]);
                        startActivity(chatActivityIntent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ParseQuery<ParseObject> queryForUsername = ParseQuery.getQuery("Message");
                queryForUsername.whereEqualTo("recipient" , ParseUser.getCurrentUser().getUsername() );
                queryForUsername.orderByDescending("createdAt");
                queryForUsername.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            SIZE = objects.size();
                            sendernames = new String[SIZE];
                            ObjectId = new String[SIZE];


                        //    dateArr=new String[SIZE];

                            int i=0;
                            if(objects.size()>0){

                                for(ParseObject UserInfo : objects){
                                    String senderName = UserInfo.getString("sender");
                                    String ObjectID = UserInfo.getObjectId();
                                    Date msgDate=UserInfo.getDate("createdAt");
                                    String strDate=msgDate+"";
                                    ObjectId[i] = ObjectID;
                          //          dateArr[i]=strDate;

                                    sendernames[i] = senderName;

                                }
                                int k,j=0,count=0;
                                for(k=0;k<SIZE;k++)
                                {
                                    if(sendernames[k]==sendernames[k+1])
                                    {k++;}
                                    else
                                    {count++;}
                                }

                                j=0;finalsendernames=new String[count];
                                for(k=0;k<SIZE;k++)
                                {
                                    if(sendernames[k]==sendernames[k+1])
                                    {k++;}
                                    else
                                    {finalsendernames[j]=sendernames[k];j++;
                                        recyclerView.setAdapter(new AdapterMsg(finalsendernames ));
                                    }
                                }


                            }
                        }
                    }
                });

/*                int k,j=0,count=0;
                for(k=0;k<SIZE;k++)
                {
                    if(sendernames[k]==sendernames[k+1])
                    {k++;}
                    else
                    {count++;}
                }

                j=0;finalsendernames=new String[count];
                for(k=0;k<SIZE;k++)
                {
                    if(sendernames[k]==sendernames[k+1])
                    {k++;}
                    else
                    {finalsendernames[j]=sendernames[k];j++;
                        recyclerView.setAdapter(new AdapterMsg(finalsendernames ));
                    }
                }
*/

                pullToRefresh.setRefreshing(false);
            }
        });


    }

}


