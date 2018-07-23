package com.example.user.asntracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    static TextView msgTV;
    List<Driver> senders;
    ArrayAdapter<String> adapter;
    Tracker currentTracker;
    int currentTrackerID;

    public RequestsFragment() {
        // Required empty public constructor
    }

    public static RequestsFragment newInstance(Tracker currentUser) {
        RequestsFragment fragment = new RequestsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentUser", currentUser);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        msgTV = getView().findViewById(R.id.msg_tv);

        //  ====>>>>> currentDriver is the receiver
        if(getArguments() != null)
        {
            currentTracker = (Tracker) getArguments().getSerializable("currentUser");
            currentTrackerID = currentTracker.getID();
        }

        senders = new ArrayList<>();

        ListView lv = (ListView)getView().findViewById(R.id.connectionRequests_ListView);
        ArrayList<String> sendersUsernames = new ArrayList<>() ;
        adapter = new ArrayAdapter<String>(getView().getContext(),R.layout.connection_request_item,R.id.sender_username_tv,sendersUsernames);
        lv.setAdapter(adapter);


        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://asnasucse18.000webhostapp.com/RFTDA/SeeRequestsToTracker.php";
        RequestParams params = new RequestParams();
        Log.d("ConnectionRequests","current user ID "+ currentTrackerID);
        params.put("receiverID",currentTrackerID);
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
                    Log.d("ConnectionRequests","onSuccess json error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ConnectionRequests","onFailure");
                msgTV.setText("error in the request");
            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int i,long l)
            {
                Intent intent= new Intent(getView().getContext(),ConnectionSenderProfileActivity.class);
                intent.putExtra("profileOwner",senders.get(i));
                intent.putExtra("currentUser", currentTracker);
                startActivity(intent);
            }
        });


    }

    private void handleResponse(JSONObject response) throws JSONException
    {
        int requestsNumber = response.getJSONObject("result").getInt("ConnectionRequestsNumber");
        if(requestsNumber == 0)
            msgTV.setText("No requests");
        else
        {
            adapter.clear();
            for (int i=0;i<requestsNumber;i++)
            {
                String key = "sender"+Integer.toString(i+1);
                int senderID = response.getJSONObject("result").getJSONObject(key).getInt("senderID");
                String senderUsername = response.getJSONObject("result").getJSONObject(key).getString("senderUsername");
                String senderEmail = response.getJSONObject("result").getJSONObject(key).getString("senderEmail");
                String senderPhonenumber = response.getJSONObject("result").getJSONObject(key).getString("senderPhonenumber");
                String senderStatus = response.getJSONObject("result").getJSONObject(key).getString("senderStatus");
                String senderToken = response.getJSONObject("result").getJSONObject(key).getString("senderToken");


                Driver sender =new Driver(senderID,senderUsername,senderEmail,senderToken,senderPhonenumber,senderStatus);
                adapter.add((i+1)+"- "+senderUsername);
                Log.d("ConnectionRequests","handleResponse profileOwner is "+sender.toString());
                senders.add(sender);

            }
        }

    }

    //TODO:- add back functionality to go to Connections requests activity


}
