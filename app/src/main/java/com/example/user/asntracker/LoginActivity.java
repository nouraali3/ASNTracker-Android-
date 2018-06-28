package com.example.user.asntracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.user.asntracker.DataTypes.Driver;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {


    String currentUserEmail ;
    String currentUserPassword ;

    EditText emailET;
    EditText passwordTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.email_et) ;
        passwordTV = findViewById(R.id.password_et);


    }

    public void login (View v)
    {
        currentUserEmail = emailET.getText().toString().trim();
        currentUserPassword = passwordTV.getText().toString().trim();
        Log.d("LoginActivity","email is "+currentUserEmail+" password is "+currentUserPassword);
        AsyncHttpClient client = new AsyncHttpClient();
        String url= "http://asnasucse18.000webhostapp.com/RFTDA/Login.php";
        RequestParams params = new RequestParams();
        params.put("email",currentUserEmail);
        params.put("password",currentUserPassword);
        client.get(url,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try
                {
                    if(response.getBoolean("error")==false)
                    {
                        Driver currentUser = new Driver(response.getInt("ID"),response.getString("email"),response.getString("username"),response.getString("token"),response.getString("phoneNumber"),response.getString("status"));
                        Log.d("LoginActivity","current user is "+currentUser.toString());
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        i.putExtra("currentUser",currentUser);
                        startActivity(i);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });

    }
}
