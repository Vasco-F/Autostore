package com.example.controllers;

import java.time.Year;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.example.models.Vehicle;
import com.example.exceptions.InvalidRegistrationYearException;
import com.example.exceptions.VehicleNotFoundException;
import com.example.repositories.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehiclesController {
	
	@Autowired
	private VehicleRepository catalog;
	
	// public VehiclesController(VehicleRepository catalog) {
	// 	this.catalog = catalog;
	// }

	@GetMapping("/")
	public String getTest(){
		return "Service up and running!";
	}
	
	@GetMapping("/vehicles")
	public List<Vehicle> getVehicles(){
		ArrayList<Vehicle> foundVehicles = (ArrayList<Vehicle>) this.catalog.findAll();

		return foundVehicles;
	}

	@GetMapping("/vehicles/{id}")
	public Vehicle getVehicle(@PathVariable Long id){

		Optional<Vehicle> result = this.catalog.findById(id);

		if(result.isPresent())
			return result.get();
		else
			throw new VehicleNotFoundException(id);
	}

	// @GetMapping("/vehicles")
	// public List<Vehicle> getVehicles(@RequestParam(value = "manufacturer") String manufacturer, 
	// 		@RequestParam(value = "model") String model,
	// 		@RequestParam(value = "year") short year ){
		
	// 	if(manufacturer == null || model == null)
	// 		System.out.println("Error: Parameters not properly filled");
		
	// 	if(year < 1886 || year > Year.now().getValue())
	// 		System.out.println("Error: Vehicle registration year is invalid");
		
	// 	List<Vehicle> foundVehicles = (ArrayList<Vehicle>) this.catalog.findByManufacturerAndModelAndYear(manufacturer, model, year);
		
	// 	if(foundVehicles.size() == 0)
	// 		throw new VehicleNotFoundException(manufacturer, model, year);
	// 	else
	// 		return foundVehicles;
	// }
	
	@PostMapping("/vehicles")
	public void insertVehicle(@RequestBody Vehicle vehicle) {
		
			//Should replace the sysout with a proper http error response
			if(vehicle.getManufacturer() == null || vehicle.getModel() == null)
				System.out.println("Error: Parameters not properly filled");
			
			if(vehicle.getYear() < 1886 || vehicle.getYear() > Year.now().getValue())
				throw new InvalidRegistrationYearException();
			
			this.catalog.save(vehicle);	
	}
	
	@PutMapping("/vehicles/{id}")
	public void updateVehicle(@RequestBody Vehicle requestVehicle, @PathVariable long id) {
		
			Optional<Vehicle> result = this.catalog.findById(id);
			
			if(result.isPresent()) {
				Vehicle vehicle = result.get();
				
				if(requestVehicle.getManufacturer() != null)
					vehicle.setManufacturer(requestVehicle.getManufacturer());
				if(requestVehicle.getModel() != null)
					vehicle.setModel(requestVehicle.getModel());
				if(requestVehicle.getYear() != 0)
					vehicle.setYear(requestVehicle.getYear());
				if(requestVehicle.getConsumption() != 0) {
					vehicle.setConsumption(requestVehicle.getConsumption());
				}
				
				this.catalog.save(vehicle);
			}else {
				throw new VehicleNotFoundException(id);
			}
	}
	
	@DeleteMapping("/vehicles/{id}")
	public void deleteVehicle(@PathVariable long id ) {
		this.catalog.deleteById(id);
	}
}
