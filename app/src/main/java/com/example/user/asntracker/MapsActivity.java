package com.example.user.asntracker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.user.asntracker.DataTypes.Driver;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Driver friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        friend= (Driver) getIntent().getSerializableExtra("friend");

        fetchAndDisplayRecentPosition();


    }

    private void fetchAndDisplayRecentPosition()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://asnasucse18.000webhostapp.com/RFTDA/FetchRecentLocation.php";
        RequestParams params= new RequestParams();
        Log.d("MapsActivity","fetchAndDisplayRecentPosition friend id "+friend.getID());
        params.put("driverID",friend.getID());
        client.get(url,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try
                {
                    Log.d("MapsActivity","fetchAndDisplayRecentPosition onSuccess response is "+response);
                    LatLng position= getPosition(response);
                    displayPositionOnMap(position);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("MapsActivity","fetchAndDisplayRecentPosition onfailure error is "+errorResponse);
            }
        });
    }


    private LatLng getPosition(JSONObject response) throws JSONException {
        double latitude = response.getJSONObject("driverRecentLocation").getDouble("latitude");
        double longitude = response.getJSONObject("driverRecentLocation").getDouble("longitude");
        return new LatLng(latitude,longitude);
    }

    private void displayPositionOnMap(LatLng position)
    {
        Log.d("MapsActivity","position of friend is "+position.latitude+" "+position.longitude);
        mMap.addMarker(new MarkerOptions().position(position).title(friend.getUserName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
    }

    public void refreshLocationOnMap(View v)
    {
        fetchAndDisplayRecentPosition();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
    }
}
