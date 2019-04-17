package com.example.sahyog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ProposalViewActivity extends AppCompatActivity {

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

        intent = getIntent();
        mapDirIntent = new Intent(getApplicationContext(),mapDirectionActivity.class);
       // String userNam = intent.getStringExtra("userNames");
       // Double lat = intent.getDoubleExtra("lat",30.11);
        //Double long1 = intent.getDoubleExtra("long",30.11);
       // Log.i("usrnInfo " ,userNam );
        mapDirIntent.putExtra("userName", intent.getStringExtra("userNames"));
        mapDirIntent.putExtra("lat", intent.getDoubleExtra("lat",30.11));
        mapDirIntent.putExtra("long", intent.getDoubleExtra("long",30.11));
    }
}
