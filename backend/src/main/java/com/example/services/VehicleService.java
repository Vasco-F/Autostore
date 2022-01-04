package com.example.services;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.dtos.RoadtripDTO;
import com.example.dtos.VehicleDTO;
import com.example.exceptions.InvalidRegistrationYearException;
import com.example.exceptions.InvalidVehicleRegistration;
import com.example.exceptions.InvalidVehicleRegistrationInfo;
import com.example.exceptions.VehicleNotFoundException;
import com.example.models.Vehicle;
import com.example.repositories.FileSystemRepository;
import com.example.repositories.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VehicleService {
    
    @Autowired
    private FileSystemRepository fileRepository;

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

    public RoadtripDTO getTripCost(Long id, float distance, float fuelPrice){
        Optional<Vehicle> vehicleResult = this.vehicleRepo.findById(id);

        VehicleDTO vehicleDTO = convertToVehicleDTO(vehicleResult.orElseThrow(() -> { throw new VehicleNotFoundException(id);}));
        float cost = (distance * vehicleDTO.getConsumption() / 100) * fuelPrice;

        return new RoadtripDTO(vehicleDTO, distance, fuelPrice, cost);
    }

    public Long insert(VehicleDTO vehicle, MultipartFile file) throws InvalidVehicleRegistration {
        
        if(vehicle.getManufacturer() == null || vehicle.getModel() == null)
            throw new InvalidVehicleRegistrationInfo("Error: Parameters not properly filled");
        
        if(vehicle.getYear() < 1886 || vehicle.getYear() > Year.now().getValue())
            throw new InvalidRegistrationYearException();

        Vehicle savedVehicle = this.vehicleRepo.save(convertToVehicle(vehicle));

        String filePath;
        try{
            filePath = this.fileRepository.save(file.getBytes(), String.valueOf(savedVehicle.getId()));
        }catch (IOException e){
            this.vehicleRepo.delete(savedVehicle);

            throw new InvalidVehicleRegistration();
        }
        
        savedVehicle.setImage(filePath);

        return this.vehicleRepo.save(savedVehicle).getId();	
    }

    /**
     * This method updates the whole vehicle fields
     * @param requestVehicle The DTO containing the new fields to update
     * @param id The vehicle id to update
     */
    public void update(VehicleDTO requestVehicle, Long id){
        Optional<Vehicle> result = this.vehicleRepo.findById(id);
			
        result.ifPresentOrElse(vehicle -> {
            vehicle.setManufacturer(requestVehicle.getManufacturer());
            vehicle.setModel(requestVehicle.getModel());
            vehicle.setYear(requestVehicle.getYear());
            vehicle.setConsumption(requestVehicle.getConsumption());
            vehicle.setImage(requestVehicle.getImage());

            this.vehicleRepo.save(vehicle);
        }, () -> {
            throw new VehicleNotFoundException(id);
        });
    }

    public void deleteById(Long id){
        this.vehicleRepo.deleteById(id);
    }

    /**
     * 
     * @param vehicle
     * @return
     */
    private Vehicle convertToVehicle(VehicleDTO vehicle){
        Vehicle mappedEntity = new Vehicle();

        if(vehicle.hasId())
            mappedEntity.setId(vehicle.getVehicleId());
        
        mappedEntity.setManufacturer(vehicle.getManufacturer());
        mappedEntity.setModel(vehicle.getModel());
        mappedEntity.setYear(vehicle.getYear());
        mappedEntity.setConsumption(vehicle.getConsumption());
        mappedEntity.setImage(vehicle.getImage());

        return mappedEntity;
    }


    /**
     * 
     * @param vehicle
     * @return
     */
    private VehicleDTO convertToVehicleDTO(Vehicle vehicle){
        VehicleDTO mappedDTO = new VehicleDTO();

        mappedDTO.setVehicleId(vehicle.getId());
        mappedDTO.setManufacturer(vehicle.getManufacturer());
        mappedDTO.setModel(vehicle.getModel());
        mappedDTO.setYear(vehicle.getYear());
        mappedDTO.setConsumption(vehicle.getConsumption());
        mappedDTO.setImage(vehicle.getImage());

        return mappedDTO;
    }
}
