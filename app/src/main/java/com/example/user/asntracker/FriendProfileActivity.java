package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FriendProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
    }


    public void startTrackActivity(View v)
    {startTrackingIntent();}

    private void startTrackingIntent( )
    {
        Intent i=new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(i);
    }




}
