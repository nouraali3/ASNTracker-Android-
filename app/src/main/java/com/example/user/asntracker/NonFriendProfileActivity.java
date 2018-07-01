package com.example.user.asntracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.asntracker.DataTypes.Driver;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NonFriendProfileActivity extends AppCompatActivity {


    static Driver currentUser;
    static Driver profileOwner;
    Button addButton;
    boolean cancel=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_friend_profile);

        profileOwner= (Driver) getIntent().getSerializableExtra("matchedDriver");
        currentUser = (Driver) getIntent().getSerializableExtra("currentUser");

        displayDriverInformation(profileOwner);

        //TODO: check if the driver is already a connection =>  remove add button addButton.setVisibility(VISIBLE.GONE);
        //TODO: check if a request is previously sent to this driver => addButton.setText("Cancel Request");
    }

    private void displayDriverInformation(Driver driver)
    {
        TextView nonFriendNameTV = findViewById(R.id.nonFriendName_tv);
        nonFriendNameTV.setText(profileOwner.getUserName());
        TextView nameTV = findViewById(R.id.name2_tv);
        TextView emailTV= findViewById(R.id.email2_tv);
        nameTV.setText(profileOwner.getUserName());
        emailTV.setText(profileOwner.getEmail());
    }

    //send connection request

    public void addFriend(View v)
    {
        if(!cancel)
        {
            AsyncHttpClient httpClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("senderID",currentUser.getID());
            params.put("receiverID",profileOwner.getID());
            Log.d("NnFriendProfileActivity","senderID "+currentUser.getID()+" receiver ID "+profileOwner.getID());
            final boolean[] requestSuccess = {false};
            httpClient.get("http://asnasucse18.000webhostapp.com/RFTDA/SendFriendRequest.php",params, new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response)
                {
                    addButton =(Button) findViewById(R.id.add_btn);
                    addButton.setText("Cancel Request");
                    cancel=true;
                    Log.d("NnFriendProfileActivity","addFriend connection request is sent successfuly");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("NnFriendProfileActivity","sendConnectionRequestTo onFailure");
                }
            });
        }
        else
        {
            AsyncHttpClient httpClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("senderID",currentUser.getID());
            params.put("receiverID",profileOwner.getID());
            Log.d("NnFriendProfileActivity","senderID "+currentUser.getID()+" receiver ID "+profileOwner.getID());
            final boolean[] requestSuccess = {false};
            httpClient.get("http://asnasucse18.000webhostapp.com/RFTDA/CancelFriendRequest.php",params, new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response)
                {
                    addButton =(Button) findViewById(R.id.add_btn);
                    addButton.setText("ADD");
                    cancel=false;
                    Log.d("NnFriendProfileActivity","addFriend connection request is sent successfuly");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("NnFriendProfileActivity","sendConnectionRequestTo onFailure");
                }
            });
        }

    }
}
