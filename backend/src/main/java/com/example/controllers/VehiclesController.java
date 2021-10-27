package com.example.controllers;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.example.models.Vehicle;
import com.example.exceptions.InvalidRegistrationYearException;
import com.example.exceptions.VehicleNotFoundException;
import com.example.repositories.VehicleRepository;
import com.example.services.VehicleService;

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
	private VehicleService service;
	
	@GetMapping("/vehicles")
	public List<Vehicle> getVehicles(){
		
		return service.getAll();
	}

	@GetMapping("/vehicles/{id}")
	public Vehicle getVehicle(@PathVariable Long id) throws IOException{

		return service.getById(id);
	}
	
	@PostMapping("/vehicles")
	public Long insertVehicle(@RequestBody Vehicle vehicle) {
		
		return service.insert(vehicle);
	}
	
	@PutMapping("/vehicles/{id}")
	public void updateVehicle(@RequestBody Vehicle requestVehicle, @PathVariable long id) {
		
		service.update(requestVehicle, id);
	}
	
	@DeleteMapping("/vehicles/{id}")
	public void deleteVehicle(@PathVariable long id ) {
		service.deleteById(id);
	}
}
