package com.example.user.asntracker.DataTypes;

import java.io.Serializable;

/**
 * Created by user on 15/06/2018.
 */

public class NonDriver implements Serializable{
    int personID;
    String email;
    String password;
    String token;

    public NonDriver(int personID, String email, String password)
    {
        this.personID = personID;
        this.email = email;
        this.password = password;
    }

    public NonDriver(int personID, String email, String password, String token) {
        this.personID = personID;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public int getPersonID() {return personID;}
    public void setPersonID(int personID) {this.personID = personID;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
