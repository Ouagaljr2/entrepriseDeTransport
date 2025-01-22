package com.entreprise.transport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.TripRepository;
import com.entreprise.transport.repository.VehicleRepository;

//Services
@Service
public class DriverService {
	private final DriverRepository driverRepository;
    private final TripRepository tripRepository;  // Injecter le repository des trajets
    private final VehicleRepository vehicleRepository;  // Injecter le repository des véhicules


	public DriverService(DriverRepository driverRepository, TripRepository tripRepository, VehicleRepository vehicleRepository) {
		this.driverRepository = driverRepository;
		this.tripRepository = tripRepository;
		this.vehicleRepository = vehicleRepository;
	}

	public List<Driver> findAllDrivers() {
		return driverRepository.findAll();
	}

	public Driver saveDriver(Driver driver) {
		System.out.println("Saving driver: " + driver);
		return driverRepository.save(driver);
	}
	

	public Driver updateDriver(int id, Driver updatedDriver) {
		System.out.println("Updating driver with id: " + id);
		return driverRepository.findById(id).map(driver -> {
			driver.setName(updatedDriver.getName());
			driver.setLicenseNumber(updatedDriver.getLicenseNumber());
			driver.setPhoneNumber(updatedDriver.getPhoneNumber());
			driver.setEmail(updatedDriver.getEmail());
			driver.setStatus(updatedDriver.getStatus());
			return driverRepository.save(driver);
		}).orElseThrow(() -> new RuntimeException("Driver not found"));
	}
	
	@Transactional
	public void deleteDriver(int id) {
	    // Vérifier s'il y a des trajets associés au conducteur
	    List<Trip> trips = tripRepository.findByDriverId(id);
	    if (!trips.isEmpty()) {
	        // Mettre à jour le statut du véhicule à "Disponible" pour chaque trajet associé
	        for (Trip trip : trips) {
	            Vehicle vehicle = trip.getVehicle();  // Récupérer le véhicule du trajet
	            if (vehicle != null) {
	                // Mettre à jour le statut du véhicule à "Disponible"
	                vehicle.setStatus("Disponible");
	                // Sauvegarder les changements apportés au véhicule
	                vehicleRepository.save(vehicle);
	            }
	        }
	        // Supprimer tous les trajets associés avant de supprimer le conducteur
	        tripRepository.deleteAll(trips);
	    }

	    // Supprimer le conducteur
	    driverRepository.deleteById(id);
	}


	public List<Driver> searchDrivers(String name) {
		System.out.println("Searching drivers with name: " + name);
		return driverRepository.findByNameContaining(name);
	}
}