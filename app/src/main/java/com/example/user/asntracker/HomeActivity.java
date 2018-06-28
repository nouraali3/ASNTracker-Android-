package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.asntracker.DataTypes.Driver;
import com.example.user.asntracker.DataTypes.NonDriver;

public class HomeActivity extends AppCompatActivity {

    private static Driver currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        currentUser = (Driver) getIntent().getSerializableExtra("currentUser");
    }

   public void showFriends(View v)
   {
       Intent i= new Intent(getApplicationContext(),FriendsListActivity.class);
       i.putExtra("currentUser",currentUser);
       startActivity(i);
   }

    public void findFriendsActivity(View v)
    {startFindFriendsIntent();}

    private void startFindFriendsIntent()
    {
        Intent i=new Intent(getApplicationContext(),FindFriendsActivity.class);
        i.putExtra("currentUser",currentUser);
        startActivity(i);
    }
}
