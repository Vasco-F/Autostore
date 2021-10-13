package com.example.controllers;

import java.time.Year;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Vehicle;
import com.example.repositories.VehicleRepository;

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
	public void getVehicle(@RequestParam(value = "manufacturer") String manufacturer, 
			@RequestParam(value = "model") String model,
			@RequestParam(value = "year") short year ){
		
		if(manufacturer == null || model == null)
			System.out.println("Error: Parameters not properly filled");
		
		if(year < 1886 || year > Year.now().getValue())
			System.out.println("Error: Vehicle registration year is invalid");
		
		ArrayList<Vehicle> foundVehicles = (ArrayList<Vehicle>) this.catalog.findByManufacturerAndModelAndYear(manufacturer, model, year);
		
		if(foundVehicles.size() == 0)
			System.out.println("No found vehicles with the given parameters");
		else if(foundVehicles.size() == 1)
			System.out.println(foundVehicles);
		else 
			System.out.println("More than one vehicle was found with the given parameters");
	}
	
	@PostMapping("/vehicles")
	public void insertVehicle(@RequestParam(value = "manufacturer") String manufacturer, 
			@RequestParam(value = "model") String model,
			@RequestParam(value = "year") short year,
			@RequestParam(value = "consumption") float consumption) {
		
			//Should replace the sysout with a proper http error response
			if(manufacturer == null || model == null)
				System.out.println("Error: Parameters not properly filled");
			
			if(year < 1886 || year > Year.now().getValue())
				System.out.println("Error: Vehicle registration year is invalid");
			
			Vehicle vehicle = new Vehicle(manufacturer, model, year, consumption);
			this.catalog.save(vehicle);	
	}
	
	@PutMapping("/vehicles")
	public void updateVehicle(@RequestParam(value = "id") long id,
			@RequestParam(value = "manufacturer") Optional<String> manufacturer, 
			@RequestParam(value = "model") Optional<String> model,
			@RequestParam(value = "year") Optional<Short> year,
			@RequestParam(value = "consumption") Optional<Float> consumption) {
			
			Optional<Vehicle> result = this.catalog.findById(id);
			
			if(result.isPresent()) {
				Vehicle vehicle = result.get();
				
				if(manufacturer.isPresent())
					vehicle.setManufacturer(manufacturer.get());
				if(model.isPresent())
					vehicle.setModel(model.get());
				if(year.isPresent())
					vehicle.setYear(year.get());
				if(consumption.isPresent()) {
					vehicle.setConsumption(consumption.get());
				}
				
				this.catalog.save(vehicle);
			}else {
				//Later version should return an error like 404 not found
				System.out.println("No ID Found!");
			}
	}
	
	@DeleteMapping("/vehicles")
	public void deleteVehicle(@RequestParam(value = "id") long id ) {
			
		this.catalog.deleteById(id);
	}
}
