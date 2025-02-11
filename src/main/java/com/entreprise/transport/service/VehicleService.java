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

/**
 * Service gérant les opérations liées aux véhicules.
 * 
 * Ce service permet de récupérer, enregistrer, mettre à jour et supprimer des véhicules.
 * Il assure également la gestion des trajets associés aux véhicules lors de leur suppression.
 * 
 * Auteur: Ouagal Mahamat
 */
@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final TripRepository tripRepository;  // Repository des trajets
    private final DriverRepository driverRepository;  // Repository des conducteurs

    /**
     * Constructeur du service VehicleService
     * 
     * @param vehicleRepository Repository des véhicules
     * @param tripRepository Repository des trajets
     * @param driverRepository Repository des conducteurs
     */
    public VehicleService(VehicleRepository vehicleRepository, TripRepository tripRepository, DriverRepository driverRepository) {
        this.vehicleRepository = vehicleRepository;
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
    }

    /**
     * Récupère la liste de tous les véhicules.
     * 
     * @return Liste des véhicules
     */
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    /**
     * Enregistre un nouveau véhicule dans la base de données.
     * 
     * @param vehicle Le véhicule à enregistrer
     * @return Le véhicule enregistré
     */
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * Met à jour un véhicule existant.
     * 
     * @param id Identifiant du véhicule à mettre à jour
     * @param updatedVehicle Données mises à jour du véhicule
     * @return Le véhicule mis à jour
     * @throws RuntimeException si le véhicule n'est pas trouvé
     */
    public Vehicle updateVehicle(int id, Vehicle updatedVehicle) {
        return vehicleRepository.findById(id).map(vehicle -> {
            vehicle.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
            vehicle.setStatus(updatedVehicle.getStatus());
            return vehicleRepository.save(vehicle);
        }).orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    /**
     * Supprime un véhicule après avoir géré les trajets associés.
     * 
     * @param id Identifiant du véhicule à supprimer
     */
    @Transactional
    public void deleteVehicle(int id) {
        // Vérifier s'il y a des trajets associés au véhicule
        List<Trip> trips = tripRepository.findByVehicleId(id);
        if (!trips.isEmpty()) {
            // Mettre à jour le statut des conducteurs des trajets associés
            for (Trip trip : trips) {
                Driver driver = trip.getDriver();
                if (driver != null) {
                    driver.setStatus("Disponible");
                    driverRepository.save(driver);
                }
            }
            // Supprimer tous les trajets associés avant de supprimer le véhicule
            tripRepository.deleteAll(trips);
        }

        // Supprimer le véhicule
        vehicleRepository.deleteById(id);
    }

    /**
     * Recherche des véhicules par numéro d'immatriculation.
     * 
     * @param registrationNumber Numéro d'immatriculation partiel ou complet
     * @return Liste des véhicules correspondants
     */
    public List<Vehicle> searchVehicles(String registrationNumber) {
        return vehicleRepository.findByRegistrationNumberContaining(registrationNumber);
    }
}
