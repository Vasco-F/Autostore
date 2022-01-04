package com.example.controllers;

import java.io.IOException;
import java.util.List;

import com.example.dtos.RoadtripDTO;
import com.example.dtos.VehicleDTO;
import com.example.repositories.FileSystemRepository;
import com.example.services.VehicleService;

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
	private VehicleService service;
	
	@GetMapping("/vehicles")
	public List<VehicleDTO> getVehicles(){
		
		return service.getAll();
	}

	@GetMapping("/vehicles/{id}")
	public VehicleDTO getVehicle(@PathVariable Long id) throws IOException{

		return service.getById(id);
	}

	@GetMapping("/vehicles/{id}/roadtrip-cost")
	public RoadtripDTO getVehicle(@PathVariable Long id, @RequestParam float distance, 
		@RequestParam float fuelPrice) throws IOException{

		return service.getTripCost(id, distance, fuelPrice);
	}
	
	@PostMapping("/vehicles")
	public Long insertVehicle(@RequestBody VehicleDTO vehicle, @RequestBody MultipartFile file) throws IOException {
		
		return service.insert(vehicle, file);
	}
	
	@PutMapping("/vehicles/{id}")
	public void updateVehicle(@RequestBody VehicleDTO requestVehicle, @PathVariable long id) {
		
		service.update(requestVehicle, id);
	}
	
	@DeleteMapping("/vehicles/{id}")
	public void deleteVehicle(@PathVariable long id ) {
		service.deleteById(id);
	}
}
