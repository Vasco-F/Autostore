package com.example.repositories;

import java.util.List;

import com.example.models.Vehicle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long>{
	
	List<Vehicle> findByManufacturerAndModelAndYear(String manufacturer, String model, short year);

}
	