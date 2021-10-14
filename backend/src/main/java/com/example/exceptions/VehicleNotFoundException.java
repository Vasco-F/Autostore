package com.example.exceptions;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(Long id){
        super("Vehicle with id " + id + " does not exhist");
    }

    public VehicleNotFoundException(String brand, String model, short year){
        super("No vehicle found from:\nBrand:" + brand + "\nModel:" + model + "\nYear:" + year);
    }
}