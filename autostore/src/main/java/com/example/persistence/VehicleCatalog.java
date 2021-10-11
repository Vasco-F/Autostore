package com.example.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface VehicleCatalog extends CrudRepository<Vehicle, Long>{
	
	List<Vehicle> findByManufacturerAndModelAndYear(String manufacturer, String model, short year);

}
	