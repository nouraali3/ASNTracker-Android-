package com.example.user.asntracker.DatabaseOperations;

/**
 * Created by user on 16/06/2018.
 */
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.user.asntracker.DataTypes.Driver;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DriverUtils
{
    Context context;

    public DriverUtils()
    {
    }

    public DriverUtils(Context ctx)
    {
        this.context=ctx;
    }
}
