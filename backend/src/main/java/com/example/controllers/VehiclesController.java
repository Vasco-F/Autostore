package com.example.controllers;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.example.models.Image;
import com.example.models.Vehicle;
import com.example.exceptions.InvalidRegistrationYearException;
import com.example.exceptions.VehicleNotFoundException;
import com.example.repositories.FileSystemRepository;
import com.example.repositories.ImageRepository;
import com.example.repositories.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VehiclesController {
	
	@Autowired
	private VehicleRepository vehicleRepo;

	@Autowired
	private FileSystemRepository fileRepo;

	@Autowired
	private ImageRepository pathRepo;
	
	// public VehiclesController(VehicleRepository catalog) {
	// 	this.catalog = catalog;
	// }

	@GetMapping("/")
	public String getTest(){
		return "Service up and running!";
	}
	
	@GetMapping("/vehicles")
	public List<Vehicle> getVehicles(){
		ArrayList<Vehicle> foundVehicles = (ArrayList<Vehicle>) this.vehicleRepo.findAll();

		return foundVehicles;
	}

	@GetMapping("/vehicles/{id}")
	public Vehicle getVehicle(@PathVariable Long id){

		Optional<Vehicle> result = this.vehicleRepo.findById(id);

		if(result.isPresent())
			return result.get();
		else
			throw new VehicleNotFoundException(id);
	}

	@PostMapping("/vehicles/{id}/upload")
	public Long getVehicle(@RequestParam MultipartFile file, @PathVariable Long id) throws IOException {

		byte[] fileBytes = file.getBytes();
		String fileName = file.getOriginalFilename();

		Optional<Vehicle> result = this.vehicleRepo.findById(id);

		if(result.isPresent()){
			String location = this.fileRepo.save(fileBytes, fileName);
			Vehicle vehicle = result.get();
			// Vehicle vehicle = new Vehicle();
			vehicle.setId(id);


			Image image = new Image(vehicle, location);

			return this.pathRepo.save(image).getVehicle().getId();
		}else{
			throw new VehicleNotFoundException(id);
		}
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
				if(requestVehicle.getConsumption() != 0) {
					vehicle.setConsumption(requestVehicle.getConsumption());
				}
				
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
