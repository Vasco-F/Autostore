package com.example.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String manufacturer;
	
	private String model;
	
	private short year;
	
	private float consumption;

	private String image;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User owner;
	
	public Vehicle(){}

	public Vehicle(String manufacturer, String model, short year, float consumption, User user) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.year = year;
		this.consumption = consumption;
	}

	public Vehicle(String manufacturer, String model, short year, float consumption, String path, User user) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.year = year;
		this.consumption = consumption;
		this.image = path;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getImage(){
		return this.image;
	}

	public void setImage(String path){
		this.image = path;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
