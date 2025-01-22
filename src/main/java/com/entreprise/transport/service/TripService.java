package com.entreprise.transport.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.VehicleRepository;
import com.entreprise.transport.repository.TripRepository;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    // Injection des dépendances
    public TripService(TripRepository tripRepository, DriverRepository driverRepository, VehicleRepository vehicleRepository) {
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    public Trip saveTrip(int driverId, int vehicleId, Trip trip) {
        // Recherche des entités Driver et Vehicle par leurs ID
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        // Mise à jour du statut des entités Driver et Vehicle à "Non disponible"
        driver.setStatus("Non disponible");
        vehicle.setStatus("Non disponible");

        // Sauvegarde des entités mises à jour
        driverRepository.save(driver);
        vehicleRepository.save(vehicle);
        
        // Affectation des entités Driver et Vehicle au Trip
        trip.setDriver(driver);
        trip.setVehicle(vehicle);

        // Sauvegarde du Trip dans la base de données
        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateTrip(int id, Trip updatedTrip) {
        return tripRepository.findById(id).map(trip -> {
            // Vérifier si le conducteur a changé
            Driver oldDriver = trip.getDriver();
            Driver newDriver = updatedTrip.getDriver();
            if (oldDriver != null && !oldDriver.equals(newDriver)) {
                // Mettre à jour le statut de l'ancien conducteur à "Disponible"
                oldDriver.setStatus("Disponible");
                driverRepository.save(oldDriver);
            }

            // Vérifier si le véhicule a changé
            Vehicle oldVehicle = trip.getVehicle();
            Vehicle newVehicle = updatedTrip.getVehicle();
            if (oldVehicle != null && !oldVehicle.equals(newVehicle)) {
                // Mettre à jour le statut du véhicule à "Disponible"
                oldVehicle.setStatus("Disponible");
                vehicleRepository.save(oldVehicle);
            }

            // Mettre à jour les informations du trajet avec les nouvelles valeurs
            trip.setDriver(newDriver);
            trip.setVehicle(newVehicle);
            trip.setOrigin(updatedTrip.getOrigin());
            trip.setDestination(updatedTrip.getDestination());
            trip.setDistance(updatedTrip.getDistance());
            trip.setDate(updatedTrip.getDate());
            trip.setStatus(updatedTrip.getStatus());

            // Si un nouveau conducteur ou véhicule est assigné, mettre leur statut à "Non Disponible"
            if (newDriver != null) {
                newDriver.setStatus("Non Disponible");
                driverRepository.save(newDriver);
            }

            if (newVehicle != null) {
                newVehicle.setStatus("Non Disponible");
                vehicleRepository.save(newVehicle);
            }

            // Sauvegarder et retourner le trajet mis à jour
            return tripRepository.save(trip);
        }).orElseThrow(() -> new RuntimeException("Trip not found"));
    }


    public void deleteTrip(int id) {
        // Recherche du trajet à supprimer
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        // Récupérer le conducteur et le véhicule associés au trajet
        Driver driver = trip.getDriver();
        Vehicle vehicle = trip.getVehicle();
        
        // Mise à jour du statut des entités Driver et Vehicle à "Disponible"
        driver.setStatus("Disponible");
        vehicle.setStatus("Disponible");

        // Sauvegarder les entités mises à jour
        driverRepository.save(driver);
        vehicleRepository.save(vehicle);

        // Suppression du trajet
        tripRepository.deleteById(id);
    }


    public List<Trip> searchTrips(String origin, String destination) {
        return tripRepository.findByOriginContainingOrDestinationContaining(origin, destination);
    }
}
