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
	private VehicleRepository vehicleRepo;
	
	@GetMapping("/vehicles")
	public List<Vehicle> getVehicles(){
		ArrayList<Vehicle> foundVehicles = (ArrayList<Vehicle>) this.vehicleRepo.findAll();

		return foundVehicles;
	}

	@GetMapping("/vehicles/{id}")
	public Vehicle getVehicle(@PathVariable Long id) throws IOException{

		Optional<Vehicle> vehicleResult = this.vehicleRepo.findById(id);

		if(vehicleResult.isPresent()){

			Vehicle vehicle = vehicleResult.get();

			return vehicle;
		}else
			throw new VehicleNotFoundException(id);

		
	}
	
	@PostMapping("/vehicles")
	public Long insertVehicle(@RequestBody Vehicle vehicle) {
		
			//Should replace the sysout with a proper http error response
			if(vehicle.getManufacturer() == null || vehicle.getModel() == null)
				System.out.println("Error: Parameters not properly filled");
			
			if(vehicle.getYear() < 1886 || vehicle.getYear() > Year.now().getValue())
				throw new InvalidRegistrationYearException();
			
			return this.vehicleRepo.save(vehicle).getId();	
	}
	
	@PutMapping("/vehicles/{id}")
	public void updateVehicle(@RequestBody Vehicle requestVehicle, @PathVariable long id) {
		
			Optional<Vehicle> result = this.vehicleRepo.findById(id);
			
			if(result.isPresent()) {
				Vehicle vehicle = result.get();
				
				if(requestVehicle.getManufacturer() != null)
					vehicle.setManufacturer(requestVehicle.getManufacturer());
				if(requestVehicle.getModel() != null)
					vehicle.setModel(requestVehicle.getModel());
				if(requestVehicle.getYear() != 0)
					vehicle.setYear(requestVehicle.getYear());
				if(requestVehicle.getConsumption() != 0)
					vehicle.setConsumption(requestVehicle.getConsumption());
				if(requestVehicle.getImage() != null)
					vehicle.setImage(requestVehicle.getImage());

				
				this.vehicleRepo.save(vehicle);
			}else {
				throw new VehicleNotFoundException(id);
			}
	}
	
	@DeleteMapping("/vehicles/{id}")
	public void deleteVehicle(@PathVariable long id ) {
		this.vehicleRepo.deleteById(id);
	}
}
