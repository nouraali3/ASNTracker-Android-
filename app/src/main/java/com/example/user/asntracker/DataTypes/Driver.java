package com.example.user.asntracker.DataTypes;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import cz.msebera.android.httpclient.Header;

public class Driver implements Serializable
{
   int ID;
   String userName,email, password, phonenumber, status, token,gender;

    public Driver(int ID, String userName, String email) {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
    }


    public Driver(int ID, String userName, String email, String token, String phonenumber, String status)
    {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
        this.phonenumber = phonenumber;
        this.status = status;
        this.token = token;
    }

    public Driver(int ID, String userName, String email, String token, String phonenumber, String status, String gender)
    {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
        this.phonenumber = phonenumber;
        this.status = status;
        this.token = token;
        this.gender=gender;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "ID=" + ID +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", status='" + status + '\'' +
                ", token='" + token + '\'' +
                ", gender='" + gender +
                '}';
    }


    public String getFormattedNonFriendInfo()
    {
        return
                  "userame     :" + userName + '\'' +
                "\nemail       :" + email  ;
    }

    public String getFormattedFriendInfo()
    {
        return
                  "userame     :" + userName + '\'' +
                "\nemail       :" + email + '\'' +
                "\nphonenumber :" + phonenumber + '\'' +
                "\nstatus      :" + status  ;
    }
    public int getID() {return ID;}
    public void setID(int ID) {this.ID = ID;}

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getPhonenumber() {return phonenumber;}
    public void setPhonenumber(String phonenumber) {this.phonenumber = phonenumber;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
