package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.asntracker.DataTypes.Tracker;

public class HomeActivity extends AppCompatActivity {

    private static Tracker currentUser;

    TextView currentUsernameTV, emailTV, phoneTV, currentUsernameTV2,genderTV;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentUser = (Tracker) getIntent().getSerializableExtra("currentUser");





        setViews();
    }

    //TODO: display profile picture
    private void setViews()
    {
        currentUsernameTV = findViewById(R.id.current_user_name_tv);
        emailTV = findViewById(R.id.email_tv3);
        phoneTV = findViewById(R.id.phone_tv1);
        currentUsernameTV2 = findViewById(R.id.current_user_name_tv2);
        genderTV = findViewById(R.id.gender_tv);


        currentUsernameTV.setText(currentUser.getUserName());
        emailTV.setText(currentUser.getEmail());
        phoneTV.setText(currentUser.getPhonenumber());
        currentUsernameTV2.setText(currentUser.getUserName());
        genderTV.setText(currentUser.getGender());

    }


    public void showFriends(View v)
   {
       Intent i= new Intent(getApplicationContext(),FriendsListActivity.class);
       i.putExtra("currentUser",currentUser);
       startActivity(i);
   }

    public void findFriendsActivity(View v)
    {
        Intent i=new Intent(getApplicationContext(),FindFriendsActivity.class);
        i.putExtra("currentUser",currentUser);
        startActivity(i);
    }

    public void checkRequests(View v)
    {
        Intent i=new Intent(getApplicationContext(),ConnectionRequestsActivity.class);
        i.putExtra("currentUser",currentUser);
        startActivity(i);
    }
}
