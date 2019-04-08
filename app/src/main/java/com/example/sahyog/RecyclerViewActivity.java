package com.example.sahyog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] names = {"Akash","Ak", "Abcd" , "Uv" ,"Vipul" , "Ayush","Akash","Ak", "Abcd" , "Uv" ,"Vipul" , "Ayush","Akash","Ak", "Abcd" , "Uv" ,"Vipul" , "Ayush"};
        recyclerView.setAdapter(new AdapterProgram(names));

    }
}
