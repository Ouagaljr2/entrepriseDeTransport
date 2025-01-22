package com.entreprise.transport.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.repository.VehicleRepository;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.TripRepository;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final TripRepository tripRepository;  // Injecter le repository des trajets
    private final DriverRepository driverRepository;  // Injecter le repository des

    public VehicleService(VehicleRepository vehicleRepository, TripRepository tripRepository, DriverRepository driverRepository) {
        this.vehicleRepository = vehicleRepository;
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
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

    @Transactional
    public void deleteVehicle(int id) {
        // Vérifier s'il y a des trajets associés au véhicule
        List<Trip> trips = tripRepository.findByVehicleId(id);
        if (!trips.isEmpty()) {
            // Mettre à jour le statut du conducteur à "Disponible" pour chaque trajet associé
            for (Trip trip : trips) {
                Driver driver = trip.getDriver();  // Récupérer le conducteur du trajet
                if (driver != null) {
                    // Mettre à jour le statut du conducteur à "Disponible"
                    driver.setStatus("Disponible");
                    // Sauvegarder les changements apportés au conducteur
                    driverRepository.save(driver);
                }
            }
            // Supprimer tous les trajets associés avant de supprimer le véhicule
            tripRepository.deleteAll(trips);
        }

        // Supprimer le véhicule
        vehicleRepository.deleteById(id);
    }


    public List<Vehicle> searchVehicles(String registrationNumber) {
        System.out.println("Searching vehicles with registration number: " + registrationNumber);
        return vehicleRepository.findByRegistrationNumberContaining(registrationNumber);
    }
}
