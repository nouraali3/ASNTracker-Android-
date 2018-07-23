package com.example.user.asntracker;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.asntracker.DataTypes.Driver;
import com.example.user.asntracker.DataTypes.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {


    String currentUserEmail ;
    String currentUserPassword ;

    EditText emailET, passwordTV;
    TextView msgTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.email_et) ;
        passwordTV = findViewById(R.id.password_et);
        msgTV = findViewById(R.id.msg_tv2);


    }

    public void login (View v)
    {
        findViewById(R.id.login_btn).setBackground(getResources().getDrawable(R.drawable.btn_clicked));
        if(!connected())
        {
            msgTV.setText("No internet connection");
        }
        else
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
                    findViewById(R.id.login_btn).setBackground(getResources().getDrawable(R.drawable.button_border));
                    try
                    {
                        if(response.getBoolean("error")==false)
                        {
                            Log.d("LoginActivity", "onSuccess response is "+response);
                            Tracker currentUser = new Tracker(response.getInt("ID"),response.getString("username"),response.getString("phoneNumber"),response.getString("status"),response.getString("gender"),response.getString("email"));
                            Log.d("LoginActivity","current user is "+currentUser.toString());
                            Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                            i.putExtra("currentUser",currentUser);
                            startActivity(i);
                        }
                        else
                        {
                            msgTV.setText("wrong email or password");
                            Log.d("LoginActivity","response is " + response.toString());
                        }

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        Log.d("LoginActivity","response is " + response.toString());

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
                {
                    findViewById(R.id.login_btn).setBackground(getResources().getDrawable(R.drawable.button_border));
                    msgTV.setText("Error in connecting with server");
                    Log.d("LoginActivity","response is " + errorResponse);
                }
            });
        }


    }

    public void goToSignupActivity(View v)
    {
        startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
    }

    private boolean connected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo1 = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo networkInfo2 = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(networkInfo1.isConnected() || networkInfo2.isConnected())
            return true;
        return false;
    }
}
