package com.example.dtos;

public class VehicleDTO {

    private Long vehicleId;

    private String manufacturer;

    private String model;

    private short year;

    private float consumption;

    private String image;

    public VehicleDTO(){}

    public VehicleDTO(Long vehicleId, String manufacturer, String model, short year, float consumption, String image) {

        this.vehicleId = vehicleId;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.consumption = consumption;
        this.image = image;

    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }    

    public boolean hasId(){
        return this.vehicleId != null;
    }
}
