package com.entreprise.transport.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.repository.VehicleRepository;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(int id, Vehicle updatedVehicle) {
    	System.out.println("Updating vehicle with id: " + id);
        return vehicleRepository.findById(id).map(vehicle -> {
            vehicle.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
            vehicle.setStatus(updatedVehicle.getStatus());
            return vehicleRepository.save(vehicle);
        }).orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> searchVehicles(String registrationNumber) {
    	System.out.println("Searching vehicles with registration number: " + registrationNumber);
        return vehicleRepository.findByRegistrationNumberContaining(registrationNumber);
    }
}