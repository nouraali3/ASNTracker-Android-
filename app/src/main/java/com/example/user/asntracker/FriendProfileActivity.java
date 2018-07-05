package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.asntracker.DataTypes.Driver;
import com.example.user.asntracker.DataTypes.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FriendProfileActivity extends AppCompatActivity {

    Driver profileOwner;
    Tracker currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        profileOwner = (Driver) getIntent().getSerializableExtra("clickedFriend");
        currentUser = (Tracker) getIntent().getSerializableExtra("currentUser");
        displayFriendInfo(profileOwner);

    }

    private void displayFriendInfo(Driver profileOwner)
    {
        TextView friendNameTV = findViewById(R.id.friendName_tv);
        friendNameTV.setText(profileOwner.getUserName());
        TextView emailTV = findViewById(R.id.email_tv);
        TextView phoneTV = findViewById(R.id.phone_tv);
        TextView statusTV = findViewById(R.id.status_tv);
        emailTV.setText(profileOwner.getEmail());
        phoneTV.setText(profileOwner.getPhonenumber());
        statusTV.setText(profileOwner.getStatus());
    }


    public void startTrackActivity(View v)
    {startTrackingIntent();}

    private void startTrackingIntent( )
    {
        Intent i=new Intent(getApplicationContext(),MapsActivity.class);
        i.putExtra("friend",profileOwner);
        startActivity(i);
    }

    public void removeFriend(View v)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://asnasucse18.000webhostapp.com/RFTDA/RemoveConnection.php";
        RequestParams params = new RequestParams();
        params.put("senderID",currentUser.getID());
        params.put("receiverID",profileOwner.getID());
        client.get(url,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try
                {
                    Log.d("FriendProfileActivity","removeFriend onSuccess result is "+response.getBoolean("result"));
                    if (response.getBoolean("result")==true)
                    {
                        ImageButton trackBTN= findViewById(R.id.track_btn);
                        ImageButton removeBTN = findViewById(R.id.remove_btn);
                        trackBTN.setVisibility(View.GONE);
                        removeBTN.setVisibility(View.GONE);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FriendProfileActivity","removeFriend onFailure");
            }
        });
    }



}
