package com.example.user.asntracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.asntracker.DataTypes.Driver;
import com.example.user.asntracker.DataTypes.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ConnectionSenderProfileActivity extends AppCompatActivity {

    static Driver profileOwner;
    Tracker currentTracker;
    static int trackerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_sender_profile);

        if(getIntent().hasExtra("profileOwner") && getIntent().hasExtra("currentUser"))
        {
            profileOwner = (Driver) getIntent().getSerializableExtra("profileOwner");
            currentTracker = (Tracker) getIntent().getSerializableExtra("currentUser");
            trackerID = currentTracker.getID();
        }
        else
            Log.d("ConnectSenderActivity","No profileOwner or receiverId were received from the intent");

        displaySenderInformation();

    }

    private void displaySenderInformation()
    {
        TextView nameTV = findViewById(R.id.name3_tv);
        TextView emailTV= findViewById(R.id.email3_tv);
        nameTV.setText(profileOwner.getUserName());
        emailTV.setText(profileOwner.getEmail());
        TextView connectionSenderNameTV = findViewById(R.id.connectionSenderName_tv);
        connectionSenderNameTV.setText(profileOwner.getUserName());
    }

    public void acceptRequest(View v)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url ="http://asnasucse18.000webhostapp.com/RFTDA/AcceptRequestToTracker.php";
        RequestParams params = new RequestParams();
        params.put("senderID", profileOwner.getID());
        params.put("receiverID", trackerID);

        client.get(url,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try
                {
                    if(response.getBoolean("result")==true)
                    {
                        Button acceptBtn = findViewById(R.id.accept_btn);
                        Button ignoreBtn = findViewById(R.id.ignore_btn);
                        acceptBtn.setVisibility(View.INVISIBLE);
                        ignoreBtn.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        TextView msgTV = findViewById(R.id.msg_tv);
                        msgTV.setText("error in processing your request");
                        Log.d("ConnectionSenderProfile","acceptRequest onSuccess returned result is false");
                    }
                }
                catch (JSONException e) {e.printStackTrace();}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                TextView msgTV = findViewById(R.id.msg_tv);
                msgTV.setText("error in processing your request");
                Log.d("ConnectionSenderProfile","acceptRequest onFailure");
            }
        });
    }

    public void rejectRequest (View v)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url ="http://asnasucse18.000webhostapp.com/RFTDA/DeleteFriendRequestToTracker.php";
        RequestParams params = new RequestParams();
        params.put("senderID", profileOwner.getID());
        params.put("receiverID", trackerID);

        client.get(url,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {

                Button acceptBtn = findViewById(R.id.accept_btn);
                Button ignoreBtn = findViewById(R.id.ignore_btn);
                acceptBtn.setVisibility(View.INVISIBLE);
                ignoreBtn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                TextView msgTV = findViewById(R.id.msg_tv);
                msgTV.setText("error in processing your request");
                Log.d("ConnectionSenderProfile","ignoreRequest onFailure");
            }
        });
    }

}

