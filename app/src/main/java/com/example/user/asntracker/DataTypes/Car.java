package com.example.user.asntracker.DataTypes;

import java.io.Serializable;

/**
 * Created by user on 15/06/2018.
 */

public class Car implements Serializable {
   int carID;
   String model, carSerial;

    public Car(int carID, String model, String carSerial)
    {
        this.carID = carID;
        this.model = model;
        this.carSerial = carSerial;
    }

    public int getCarID() {return carID;}
    public void setCarID(int carID) {this.carID = carID;}

    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}

    public String getCarSerial() {return carSerial;}
    public void setCarSerial(String carSerial) {this.carSerial = carSerial;}
}
