package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.asntracker.DataTypes.Driver;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FriendsListActivity extends AppCompatActivity {

    Driver currentUSer;
    TextView msgTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        currentUSer = (Driver) getIntent().getSerializableExtra("currentUser");
        final ArrayList<String> friendList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item_friend,R.id.friendName_tv,friendList);
        ListView listView = (ListView) this.findViewById(R.id.friendsList_ListView);
        listView.setAdapter(adapter);

        //TODO: retrieve friends list from database , display friends in the list

        AsyncHttpClient client = new AsyncHttpClient();
        String url ="http://asnasucse18.000webhostapp.com/RFTDA/DisplayConnections.php";
        RequestParams params = new RequestParams();
        params.put("currentUserID", currentUSer.getID());
        client.get(url,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent= new Intent(getApplicationContext(),FriendProfileActivity.class);
                //TODO: pass friend object

                startActivity(intent);
            }
        });
    }


}
