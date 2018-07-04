package com.example.user.asntracker.DataTypes;

/**
 * Created by user on 04/07/2018.
 */

import java.io.Serializable;


public class Tracker implements Serializable
{
    int ID;
    String userName;
    String phonenumber;
    String token;
    String status;
    String gender;
    String email;
    String image;

    public Tracker(int ID, String userName, String phonenumber, String token,String image, String status, String gender, String email) {
        this.ID = ID;
        this.userName = userName;
        this.phonenumber = phonenumber;
        this.token = token;
        this.image = image;
        this.status = status;
        this.gender = gender;
        this.email = email;
    }

    public Tracker(int ID, String userName, String phonenumber, String status, String gender, String email) {
        this.ID = ID;
        this.userName = userName;
        this.phonenumber = phonenumber;
        this.status = status;
        this.gender = gender;
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Tracker{" +
                "ID=" + ID +
                ", userName='" + userName + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", token='" + token + '\'' +
                ", status='" + status + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

