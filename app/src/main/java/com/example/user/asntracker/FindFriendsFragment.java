package com.example.user.asntracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.asntracker.DataTypes.Driver;
import com.example.user.asntracker.DataTypes.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFriendsFragment extends Fragment {

    EditText emailEditText ;
    TextView resultTextView;
    ProgressBar progressBar;
    static String driverEmail;
    static Driver matchedDriver;
    static Tracker currentTracker;
    static Button searchButton;

    public FindFriendsFragment() {
        // Required empty public constructor
    }

    public static FindFriendsFragment newInstance(Tracker currentUser) {
        FindFriendsFragment fragment = new FindFriendsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentUser", currentUser);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_friends, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentTracker = (Tracker) getArguments().getSerializable("currentUser");
        setViews();
        progressBar.setVisibility(View.GONE);
        Log.d("FindFriendsActivity","onCreate ");

        searchButton = view.findViewById(R.id.search_btn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchButton.setBackground(getResources().getDrawable(R.drawable.btn_clicked));
                searchDrivers();
            }
        });
    }

    private void setViews()
    {
        emailEditText =getView().findViewById(R.id.email_et);
        resultTextView = getView().findViewById(R.id.drivers_tv);
        progressBar = getView().findViewById(R.id.progressBar);
    }


    //when search button is clicked
    public void searchDrivers()
    {
        Log.d("FindsFriendsActivity ", "inside searchDrivers");
        driverEmail = emailEditText.getText().toString();
        Log.d("FindsFriendsActivity ", "matchedDriver email is "+driverEmail);

        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams parameters= new RequestParams();
        parameters.put("email",driverEmail);

        httpClient.get("http://asnasucse18.000webhostapp.com/RFTDA/FetchDriver.php",parameters,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                searchButton.setBackground(getResources().getDrawable(R.drawable.button_border));
                progressBar.setVisibility(View.GONE);
                Log.d("FindsFriendsActivity ", "inside searchDrivers onSuccess");
                Log.d("FindsFriendsActivity ", "inside searchDrivers onSuccess response is "+response.toString());
                try
                {
                    int matchedDriversNumber=response.getJSONObject("drivers").getInt("matchedDriversNumber");
                    matchedDriver = getDriverFromJSON(response);
                    resultTextView.setText(matchedDriver.getUserName());
                }
                catch (JSONException e)
                {
                    resultTextView.setText("error in parsing matchedDriver information");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                searchButton.setBackground(getResources().getDrawable(R.drawable.button_border));
                progressBar.setVisibility(View.GONE);
                Log.d("FindsFriendsActivity ", "inside searchDrivers onFailure");
                Log.d("FindsFriendsActivity ", "inside searchDrivers onFailure response is "+errorResponse.toString());
                resultTextView.setText(" Failure in HTTP request");
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private Driver getDriverFromJSON(JSONObject response) throws JSONException
    {
        String driverUsername=response.getJSONObject("drivers").getJSONObject("driver").getString("driverUserName");
        String driverToken=response.getJSONObject("drivers").getJSONObject("driver").getString("driverToken");
        int driverID=response.getJSONObject("drivers").getJSONObject("driver").getInt("driverID");
        String driverPhonenumber = response.getJSONObject("drivers").getJSONObject("driver").getString("driverPhonenumber");
        String driverStatus = response.getJSONObject("drivers").getJSONObject("driver").getString("driverStatus");
        Driver driver=new Driver(driverID,driverUsername,driverEmail,driverPhonenumber,driverStatus,driverToken);
        Log.d("FindFriendsActivity", "driver info is "+driver.toString());

        return driver;
    }


    public void DisplayDriverProfile(View view)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url ="http://asnasucse18.000webhostapp.com/RFTDA/FetchTrackedPeople.php";
        RequestParams params = new RequestParams();
        params.put("currentUserID", currentTracker.getID());
        client.get(url,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try
                {
                    if(isConnection(response))
                    {
                        Intent i = new Intent(getView().getContext(),FriendProfileActivity.class);
                        i.putExtra("currentUser", currentTracker);
                        i.putExtra("clickedFriend",matchedDriver );

                        startActivity(i);
                    }

                    else
                    {
                        //ashof hal mab3otly mn el matched driver 7aga (current tracker is receiver??)
                        AsyncHttpClient client = new AsyncHttpClient();
                        String url = "http://asnasucse18.000webhostapp.com/RFTDA/SeeRequestsToTracker.php";
                        RequestParams params = new RequestParams();
                        Log.d("FindFriendsActivity","current user ID "+currentTracker.getID());
                        params.put("receiverID",currentTracker.getID());
                        client.get(url,params, new JsonHttpResponseHandler()
                        {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response1) {
                                try
                                {
                                    if(friendRequestPreviouslySentToTracker(response1))
                                    {
                                        Log.d("FindFriendsActivity","request previously sent To tracker");
                                        Intent i = new Intent(getView().getContext(),ConnectionSenderProfileActivity.class);
                                        i.putExtra("profileOwner", matchedDriver);
                                        i.putExtra("currentUser",currentTracker);

                                        startActivity(i);
                                    }
                                    else
                                    {
                                        //hashof hal ana ba3at ll driver da abl keda, hal ana ba3talo 7aga
                                        AsyncHttpClient client = new AsyncHttpClient();
                                        String url = "http://asnasucse18.000webhostapp.com/RFTDA/SeeRequests.php";
                                        RequestParams params = new RequestParams();
                                        Log.d("FindFriendsActivity","current user ID "+currentTracker.getID());
                                        params.put("receiverID",matchedDriver.getID());
                                        client.get(url,params, new JsonHttpResponseHandler()
                                        {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response2)
                                            {
                                                try {
                                                    if(friendRequestPreviouslySentToDriver(response2))
                                                    {
                                                        Log.d("FindFriendsActivity","request previously sent to Driver");
                                                        Intent i = new Intent(getView().getContext(),NonFriendProfileActivity.class);
                                                        i.putExtra("matchedDriver", matchedDriver);
                                                        i.putExtra("currentUser", currentTracker);
                                                        i.putExtra("cancel",true);
                                                        startActivity(i);
                                                    }
                                                    else
                                                    {
                                                        Intent i = new Intent(getView().getContext(),NonFriendProfileActivity.class);
                                                        i.putExtra("matchedDriver", matchedDriver);
                                                        i.putExtra("currentUser", currentTracker);
                                                        startActivity(i);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
                                            {
                                            }
                                        });

                                    }
                                }
                                catch (JSONException e)
                                {
                                    Log.d("FindFriendsActivity","onSuccess json error2");
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                Log.d("FindFriendsActivity","onFailure");
                            }
                        });

                    }
                }
                catch (JSONException e)
                {
                    Log.d("FindFriendsActivity","onSuccess json error1");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FindFriendsActivity","onFailure");
            }
        });


    }


    private boolean isConnection(JSONObject response) throws JSONException {
        Log.d("FindFriendsActivity","handleResponse response is "+response);
        int trackersNumber = response.getJSONObject("connections").getInt("ConnectionsNumber");
        if(trackersNumber == 0)
            return false;
        else
        {
            for (int i=0;i<trackersNumber;i++)
            {
                String key = "friend"+Integer.toString(i+1);
                String friendEmail = response.getJSONObject("connections").getJSONObject(key).getString("friendEmail");

                if(matchedDriver.getEmail().equals(friendEmail))
                    return true;
                Log.d("FindFriendsActivity","isConnection ");
            }
        }
        return false;

    }

    private boolean friendRequestPreviouslySentToTracker(JSONObject response)throws JSONException
    {
        int requestsNumber = response.getJSONObject("result").getInt("ConnectionRequestsNumber");
        if(requestsNumber == 0)
            return false;
        for (int i=0;i<requestsNumber;i++)
        {
            String key = "sender"+Integer.toString(i+1);
            String senderEmail = response.getJSONObject("result").getJSONObject(key).getString("senderEmail");
            if(matchedDriver.getEmail().equals(senderEmail))
            {

                Log.d("FindFriendsActivity","request previously sent To Tracker ");
                return true;
            }
        }

        return false;
    }

    private boolean friendRequestPreviouslySentToDriver(JSONObject response)throws JSONException
    {
        int requestsNumber = response.getJSONObject("result").getInt("ConnectionRequestsNumber");
        if(requestsNumber == 0)
            return false;
        for (int i=0;i<requestsNumber;i++)
        {
            String key = "sender"+Integer.toString(i+1);
            String senderEmail = response.getJSONObject("result").getJSONObject(key).getString("senderEmail");
            if(currentTracker.getEmail().equals(senderEmail))
            {
                Log.d("FindFriendsActivity","request previously sent to Driver");
                return true;
            }
        }

        return false;
    }

}
