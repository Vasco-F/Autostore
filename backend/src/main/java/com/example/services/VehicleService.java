package com.example.services;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.dtos.VehicleDTO;
import com.example.exceptions.InvalidRegistrationYearException;
import com.example.exceptions.InvalidVehicleRegistrationInfo;
import com.example.exceptions.VehicleNotFoundException;
import com.example.models.Vehicle;
import com.example.repositories.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    
    @Autowired
	private VehicleRepository vehicleRepo;

    public List<VehicleDTO> getAll(){
        return ((List<Vehicle>) this.vehicleRepo.findAll())
            .stream()
            .map(this::convertToVehicleDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO getById(Long id){
        Optional<Vehicle> vehicleResult = this.vehicleRepo.findById(id);

        return convertToVehicleDTO(vehicleResult.orElseThrow(() -> new VehicleNotFoundException(id)));
    }

    public Long insert(Vehicle vehicle) {
        //Should replace the sysout with a proper http error response
        if(vehicle.getManufacturer() == null || vehicle.getModel() == null)
            throw new InvalidVehicleRegistrationInfo("Error: Parameters not properly filled");
        
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

    private VehicleDTO convertToVehicleDTO(Vehicle vehicle){
        VehicleDTO mappedDTO = new VehicleDTO(vehicle.getId(), 
        vehicle.getManufacturer(), 
        vehicle.getModel(),
        vehicle.getYear(),
        vehicle.getConsumption());

        return mappedDTO;
    }
}
