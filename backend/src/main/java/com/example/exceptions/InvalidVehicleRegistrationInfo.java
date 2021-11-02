package com.example.exceptions;

public class InvalidVehicleRegistrationInfo extends Exception{
    
    public InvalidVehicleRegistrationInfo(String msg){
        super(msg);
    }
}
