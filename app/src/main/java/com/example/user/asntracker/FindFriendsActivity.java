package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class FindFriendsActivity extends AppCompatActivity {

    EditText emailEditText ;
    TextView resultTextView;
    ProgressBar progressBar;
    static String driverEmail;
    static Driver matchedDriver;
    static Tracker currentTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        currentTracker = (Tracker) getIntent().getSerializableExtra("currentUser");

        setViews();
        progressBar.setVisibility(View.GONE);
        Log.d("FindFriendsActivity","onCreate ");

    }

    private void setViews()
    {
        emailEditText =findViewById(R.id.email_et);
        resultTextView = findViewById(R.id.drivers_tv);
        progressBar = findViewById(R.id.progressBar);
    }

    //when search button is clicked
    public void searchDrivers(View view)
    {
        Log.d("FindsFriendsActivity ", "inside searchDrivers");
        driverEmail = emailEditText.getText().toString();
        Log.d("FindsFriendsActivity ", "matchedDriver email is "+driverEmail);

        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams parameters= new RequestParams();
        parameters.put("email",driverEmail);

        httpClient.get("http://asnasucse18.000webhostapp.com/RFTDA/FetchDriver.php",parameters,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
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

    //when a matchedDriver is selected
    public void DisplayDriverProfile(View view)
    {

        //TODO: check if the driver is already a connection =>  remove add button addButton.setVisibility(VISIBLE.GONE);
        //TODO: check if a request is previously sent to this driver => addButton.setText("Cancel Request");
        //TODO: least important: check first if they are connected => display FriendProfileActivity , else=> display NonFriendActivity
        Intent i = new Intent(getApplicationContext(),NonFriendProfileActivity.class);
        i.putExtra("matchedDriver", matchedDriver);
        i.putExtra("currentUser", currentTracker);
        startActivity(i);
    }

}
