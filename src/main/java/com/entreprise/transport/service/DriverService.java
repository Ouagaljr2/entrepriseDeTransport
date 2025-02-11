package com.entreprise.transport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.TripRepository;
import com.entreprise.transport.repository.VehicleRepository;

// Définition du service pour gérer les conducteurs

/**
 * Service pour gérer les conducteurs dans l'application.
 * 
 * Ce service permet de récupérer, enregistrer, mettre à jour et supprimer des
 * conducteurs. Il assure également la gestion des trajets et des véhicules
 * associés aux conducteurs.
 * 
 * Auteur: Ouagal Mahamat
 */

@Service
public class DriverService {

    // Injection des dépendances via Spring (repositories pour accéder aux données)
    @Autowired
    private final DriverRepository driverRepository;
    @Autowired
    private final TripRepository tripRepository;  // Repository pour les trajets
    @Autowired
    private final VehicleRepository vehicleRepository;  // Repository pour les véhicules

    // Constructeur pour initialiser les repositories
    public DriverService(DriverRepository driverRepository, TripRepository tripRepository, VehicleRepository vehicleRepository) {
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Récupère tous les conducteurs dans la base de données.
     * 
     * @return Liste de tous les conducteurs.
     */
    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    /**
     * Sauvegarde un conducteur dans la base de données.
     * 
     * @param driver Le conducteur à enregistrer.
     * @return Le conducteur enregistré.
     */
    public Driver saveDriver(Driver driver) {
        System.out.println("Saving driver: " + driver);
        return driverRepository.save(driver);
    }

    /**
     * Met à jour les informations d'un conducteur.
     * 
     * @param id L'ID du conducteur à mettre à jour.
     * @param updatedDriver Les nouvelles informations du conducteur.
     * @return Le conducteur mis à jour.
     */
    public Driver updateDriver(int id, Driver updatedDriver) {
        return driverRepository.findById(id).map(driver -> {
            driver.setName(updatedDriver.getName());
            driver.setLicenseNumber(updatedDriver.getLicenseNumber());
            driver.setPhoneNumber(updatedDriver.getPhoneNumber());
            driver.setEmail(updatedDriver.getEmail());
            driver.setStatus(updatedDriver.getStatus());
            return driverRepository.save(driver);
        }).orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    /**
     * Supprime un conducteur et met à jour les trajets et véhicules associés.
     * 
     * @param id L'ID du conducteur à supprimer.
     */
    @Transactional
    public void deleteDriver(int id) {
        // Vérifie si le conducteur a des trajets associés
        List<Trip> trips = tripRepository.findByDriverId(id);
        if (!trips.isEmpty()) {
            // Pour chaque trajet, mettre à jour le statut du véhicule à "Disponible"
            for (Trip trip : trips) {
                Vehicle vehicle = trip.getVehicle();  // Récupère le véhicule du trajet
                if (vehicle != null) {
                    vehicle.setStatus("Disponible");  // Met à jour le statut du véhicule
                    vehicleRepository.save(vehicle);  // Sauvegarde le véhicule
                }
            }
            // Supprime tous les trajets associés au conducteur
            tripRepository.deleteAll(trips);
        }

        // Supprime le conducteur de la base de données
        driverRepository.deleteById(id);
    }

    /**
     * Recherche les conducteurs par leur nom (partie du nom).
     * 
     * @param name Le nom ou la partie du nom à rechercher.
     * @return Liste des conducteurs dont le nom correspond à la recherche.
     */
    public List<Driver> searchDrivers(String name) {
        return driverRepository.findByNameContaining(name);
    }
}
