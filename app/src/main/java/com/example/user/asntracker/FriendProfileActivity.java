package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.asntracker.DataTypes.Driver;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FriendProfileActivity extends AppCompatActivity {

    Driver profileOwner;
    Driver currentUser;
    TextView friendInfoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        profileOwner = (Driver) getIntent().getSerializableExtra("clickedFriend");
        currentUser = (Driver) getIntent().getSerializableExtra("currentUser");
        displayFriendInfo(profileOwner);

    }

    private void displayFriendInfo(Driver profileOwner)
    {
        friendInfoTV = findViewById(R.id.friendInfo_tv);
        friendInfoTV.setText(profileOwner.toString());
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
                        Button trackBTN= findViewById(R.id.track_btn);
                        Button removeBTN = findViewById(R.id.remove_btn);
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
