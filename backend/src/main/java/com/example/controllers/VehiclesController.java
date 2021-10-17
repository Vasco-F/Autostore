package com.example.controllers;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import com.example.models.Vehicle;
import com.example.exceptions.InvalidRegistrationYearException;
import com.example.exceptions.VehicleNotFoundException;
import com.example.repositories.FileSystemRepository;
import com.example.repositories.VehicleRepository;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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

	// @Autowired
	// private ImageRepository pathRepo;

	// @GetMapping("/getByImgId/{id}")
	// public Image getTest(@PathVariable Long id){
	// 	Optional<Image> result = pathRepo.findById(id);

	// 	if(result.isPresent()){
	// 		Vehicle vehicle = result.get().getVehicle();
	// 		return result.get();
	// 	}

	// 	throw new VehicleNotFoundException(id);
	// 	// return "Service up and running!";
	// }
	
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

			// if(vehicle.getImage() != null){

			// 	FileSystemResource result = this.fileRepo.find(vehicle.getImage());
				
			// 	byte[] imgByteArray = FileUtils.readFileToByteArray(result.getFile());
			// 	String imgBase64 = Base64.getEncoder().encodeToString(imgByteArray);

			// 	vehicle.setImage(imgBase64);
				
			// }

			return vehicle;
		}else
			throw new VehicleNotFoundException(id);

		
	}

	// @GetMapping(value = "/vehicles/{id}/image")
	// public FileSystemResource downloadVehiclePhoto(@PathVariable Long id) throws IOException {

	// 	// Optional<Image> result = this.pathRepo.findById(id);

	// 	Optional<Vehicle> result = this.vehicleRepo.findById(id);

	// 	if(result.isPresent()){
	// 		// Image file = result.get();
	// 		// String path = file.getPath();
	// 		// return this.fileRepo.find(path);
	// 		Vehicle vehicle = result.get();

	// 		if(vehicle.getImage() != null)
	// 			return this.fileRepo.find(vehicle.getImage());
	// 		else
	// 			throw new VehicleNotFoundException(id);

	// 	}else{
	// 		throw new VehicleNotFoundException(id);
	// 	}
	// }

	// @PostMapping("/vehicles/{id}/image")
	// public Long uploadVehiclePhoto(@RequestParam MultipartFile file, @PathVariable Long id) throws IOException {

	// 	byte[] fileBytes = file.getBytes();
	// 	String fileName = file.getOriginalFilename();

	// 	Optional<Vehicle> result = this.vehicleRepo.findById(id);

	// 	if(result.isPresent()){
	// 		String location = this.fileRepo.save(fileBytes, fileName);
	// 		Vehicle vehicle = result.get();
	// 		// Vehicle vehicle = new Vehicle();
	// 		// vehicle.setId(id);

	// 		vehicle.setImage(location);


	// 		// Image image = new Image(vehicle, location);

	// 		// return this.pathRepo.save(image).getVehicle().getId();
	// 		return this.vehicleRepo.save(vehicle).getId();
	// 	}else{
	// 		throw new VehicleNotFoundException(id);
	// 	}
	// }
	
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
