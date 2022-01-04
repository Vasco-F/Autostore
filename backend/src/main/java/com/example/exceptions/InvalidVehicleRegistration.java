package com.example.exceptions;

public class InvalidVehicleRegistration extends RuntimeException {

    public InvalidVehicleRegistration(){
        super("Error while inserting the new vehicle!");
    }
}
