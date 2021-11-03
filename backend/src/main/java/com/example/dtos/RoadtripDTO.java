package com.example.dtos;

public class RoadtripDTO {
    
    private VehicleDTO vehicle;

    private float distance;
    
    private float fuelPrice;

    private float cost;

    public RoadtripDTO(){}

    public RoadtripDTO(VehicleDTO vehicle, float distance, float fuelPrice, float cost) {
        this.vehicle = vehicle;
        this.distance = distance;
        this.fuelPrice = fuelPrice;
        this.cost = cost;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(float fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }    

    
}
