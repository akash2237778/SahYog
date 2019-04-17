package com.example.sahyog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    String activeUser="";
    ArrayList<String>msgs=new ArrayList();
    ArrayAdapter arrayAdapter;
    public void sendChat(View view)
    {
        final EditText txtchat=(EditText)findViewById(R.id.txtchat);
        ParseObject message=new ParseObject("Message");

        final String messageContent=txtchat.getText().toString();

        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recipient",activeUser);
        message.put("message",messageContent);



        txtchat.setText("");

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

               // msgs.clear();

                if(e==null)
                {
                    msgs.add(messageContent);
                arrayAdapter.notifyDataSetChanged();

            }
            }
        } );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();

        //activeUser=intent.getStringExtra("username");
        activeUser="Akanksha";
        setTitle("Chat with"+activeUser);

        ListView chatListView=(ListView)findViewById(R.id.chatListView);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,msgs);

        chatListView.setAdapter(arrayAdapter);

        ParseQuery<ParseObject> query1=new ParseQuery<ParseObject>("Message");

        query1.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("recipient",activeUser);

        ParseQuery<ParseObject> query2=new ParseQuery<ParseObject>("Message");

        query2.whereEqualTo("recipient",ParseUser.getCurrentUser().getUsername());
        query2.whereEqualTo("sender",activeUser);

        List<ParseQuery<ParseObject>> queries=new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query=ParseQuery.or(queries);

        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){

                        for(ParseObject message:objects){
                            String messageContent=message.getString("message");
                            if(!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){
                                messageContent="> "+messageContent;
                            }
                            msgs.add(messageContent);
                        }
                        arrayAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

}
}