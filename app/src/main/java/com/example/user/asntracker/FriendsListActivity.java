package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.asntracker.DataTypes.Driver;
import com.example.user.asntracker.DataTypes.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FriendsListActivity extends AppCompatActivity {

    Tracker currentUSer;
    static TextView msgTV;
    ArrayAdapter<String> adapter;
    List<Driver> currentConnections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        currentUSer = (Tracker) getIntent().getSerializableExtra("currentUser");
        msgTV = findViewById(R.id.msg_tv);

        currentConnections= new ArrayList<>();

        ArrayList<String> friendList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.list_item_friend,R.id.friendName_tv,friendList);
        ListView listView = (ListView) this.findViewById(R.id.friendsList_ListView);
        listView.setAdapter(adapter);


        AsyncHttpClient client = new AsyncHttpClient();
        String url ="http://asnasucse18.000webhostapp.com/RFTDA/FetchTrackedPeople.php";
        RequestParams params = new RequestParams();
        params.put("currentUserID", currentUSer.getID());
        client.get(url,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try
                {
                    handleResponse(response);
                }
                catch (JSONException e)
                {
                    Log.d("FriendsListActivity","onSuccess, but error in parsing json response ");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                Log.d("FriendsListActivity","onFailure and error is "+errorResponse);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent= new Intent(getApplicationContext(),FriendProfileActivity.class);
                //TODO: pass friend object
                intent.putExtra("clickedFriend",currentConnections.get(i));
                intent.putExtra("currentUser",currentUSer);
                startActivity(intent);
            }
        });
    }

    private void handleResponse(JSONObject response) throws JSONException
    {
        int requestsNumber = response.getJSONObject("connections").getInt("ConnectionsNumber");
        if(requestsNumber == 0)
            msgTV.setText("No Friends or Relatives");
        else
        {
            adapter.clear();
            for (int i=0;i<requestsNumber;i++)
            {
                String key = "friend"+Integer.toString(i+1);
                int friendID = response.getJSONObject("connections").getJSONObject(key).getInt("friendID");
                String friendUsername = response.getJSONObject("connections").getJSONObject(key).getString("friendUsername");
                String friendEmail = response.getJSONObject("connections").getJSONObject(key).getString("friendEmail");
                String friendToken = response.getJSONObject("connections").getJSONObject(key).getString("friendToken");
                String friendPhonenumber = response.getJSONObject("connections").getJSONObject(key).getString("friendPhonenumber");
                String friendStatus = response.getJSONObject("connections").getJSONObject(key).getString("friendStatus");
                Driver friend =new Driver(friendID,friendUsername,friendEmail,friendToken,friendPhonenumber,friendStatus);
                adapter.add(friendUsername);
                Log.d("ConnectionRequests","handleResponse friend is "+friend.toString());
                currentConnections.add(friend);

            }
        }
    }


}
