package com.example.controllers;

import java.time.Year;
import java.util.ArrayList;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.persistence.Vehicle;
import com.example.persistence.VehicleCatalog;

@SpringBootApplication
@RestController
public class VehiclesController {
	
	private VehicleCatalog catalog;
	
	public VehiclesController(VehicleCatalog catalog) {
		this.catalog = catalog;
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
}
