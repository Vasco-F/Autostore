package com.example.services;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.exceptions.InvalidRegistrationYearException;
import com.example.exceptions.VehicleNotFoundException;
import com.example.models.Vehicle;
import com.example.repositories.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    
    @Autowired
	private VehicleRepository vehicleRepo;

    public List<Vehicle> getAll(){
        ArrayList<Vehicle> foundVehicles = (ArrayList<Vehicle>) this.vehicleRepo.findAll();

		return foundVehicles;
    }

    public Vehicle getById(Long id){
        Optional<Vehicle> vehicleResult = this.vehicleRepo.findById(id);

		if(vehicleResult.isPresent()){
			return vehicleResult.get();
		}else{
            throw new VehicleNotFoundException(id);
        }
    }

    public Long insert(Vehicle vehicle){
        //Should replace the sysout with a proper http error response
			if(vehicle.getManufacturer() == null || vehicle.getModel() == null)
            System.out.println("Error: Parameters not properly filled");
        
        if(vehicle.getYear() < 1886 || vehicle.getYear() > Year.now().getValue())
            throw new InvalidRegistrationYearException();
        
        return this.vehicleRepo.save(vehicle).getId();	
    }

    public void update(Vehicle requestVehicle, Long id){
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

    public void deleteById(Long id){
        this.vehicleRepo.deleteById(id);
    }
}
