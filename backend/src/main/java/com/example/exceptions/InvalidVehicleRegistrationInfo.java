package com.example.exceptions;

public class InvalidVehicleRegistrationInfo extends RuntimeException{
    
    public InvalidVehicleRegistrationInfo(String msg){
        super(msg);
    }
}
