package com.entreprise.transport.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.VehicleRepository;
import com.entreprise.transport.repository.TripRepository;

/**
 * Service pour gérer les trajets dans l'application.
 * 
 * Ce service permet de récupérer, enregistrer, mettre à jour et supprimer des
 * trajets. Il assure également la gestion des conducteurs et des véhicules
 * associés aux trajets.
 * 
 * Auteur: Ouagal Mahamat
 */

@Service
public class TripService {
    // Injection de dépendances pour les repositories et services
    @Autowired
    private final TripRepository tripRepository;
    @Autowired
    private final DriverRepository driverRepository;
    @Autowired
    private final VehicleRepository vehicleRepository;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final Distance distanceService;

	/**
	 * Constructeur pour l'injection de dépendances.
	 * 
	 * @param tripRepository    Le repository pour les trajets
	 * @param driverRepository  Le repository pour les conducteurs
	 * @param vehicleRepository Le repository pour les véhicules
	 * @param emailService      Le service pour l'envoi d'emails
	 * @param distance          Le service pour le calcul de distance
	 */
    public TripService(TripRepository tripRepository, DriverRepository driverRepository,
            VehicleRepository vehicleRepository, EmailService emailService, Distance distance) {
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
        this.emailService = emailService;
        this.distanceService = distance;
    }
    
    /**
     * Récupère tous les trajets enregistrés dans la base de données.
     * 
     * @return Liste de tous les trajets.
     */
    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    /**
     * Crée un nouveau trajet en associant un conducteur et un véhicule, 
     * en calculant la distance entre les villes d'origine et de destination,
     * et en envoyant un email au conducteur.
     * 
     * @param driverId Identifiant du conducteur.
     * @param vehicleId Identifiant du véhicule.
     * @param trip Objet contenant les informations du trajet à créer.
     * @return Le trajet nouvellement créé et enregistré dans la base de données.
     */
    @Transactional
    public Trip saveTrip(int driverId, int vehicleId, Trip trip) {
        // Recherche des entités Driver et Vehicle par leurs ID
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Mise à jour du statut des entités Driver et Vehicle à "Non disponible"
        driver.setStatus("Non disponible");
        vehicle.setStatus("Non disponible");

        // Sauvegarde des entités mises à jour
        driverRepository.save(driver);
        vehicleRepository.save(vehicle);

        // Calcul de la distance entre les deux villes (origine et destination)
        try {
            String origin = trip.getOrigin(); // Exemple : "Paris"
            String destination = trip.getDestination(); // Exemple : "Lyon"

            double distance = distanceService.calculateDistance(origin, destination);
            trip.setDistance(distance); // Ajouter la distance au Trip
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul de la distance : " + e.getMessage());
        }

        // Affectation des entités Driver et Vehicle au Trip
        trip.setDriver(driver);
        trip.setVehicle(vehicle);

        // Sauvegarde du Trip dans la base de données
        Trip savedTrip = tripRepository.save(trip);

        // Envoi d'un email générique au conducteur avec les détails du trajet
        try {
            if (driver.getEmail() != null && !driver.getEmail().isEmpty()) {
                emailService.sendEmail(savedTrip); // Envoi de l'email au conducteur avec origine et destination
            } else {
                System.out.println("Email du conducteur non disponible");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }

        return savedTrip;
    }

    /**
     * Met à jour les informations d'un trajet existant tout en conservant les entités associées (conducteur et véhicule).
     * Calcul de la distance avant la mise à jour et envoi d'un email au conducteur après la mise à jour.
     * 
     * @param id Identifiant du trajet à mettre à jour.
     * @param updatedTrip Objet contenant les nouvelles informations du trajet.
     * @return Le trajet mis à jour dans la base de données.
     */
    @Transactional
    public Trip updateTrip(int id, Trip updatedTrip) {
        return tripRepository.findById(id).map(trip -> {
            // Conserver l'ancien conducteur
            Driver oldDriver = trip.getDriver();
            if (oldDriver != null) {
                trip.setDriver(oldDriver);
            }

            // Conserver l'ancien véhicule
            Vehicle oldVehicle = trip.getVehicle();
            if (oldVehicle != null) {
                trip.setVehicle(oldVehicle);
            }

            // Mettre à jour uniquement les autres champs du trajet
            trip.setOrigin(updatedTrip.getOrigin());
            trip.setDestination(updatedTrip.getDestination());
            trip.setDate(updatedTrip.getDate());
            trip.setStatus(updatedTrip.getStatus());

            // Calculer la distance avant la mise à jour
            try {
                double calculatedDistance = distanceService.calculateDistance(updatedTrip.getOrigin(),
                        updatedTrip.getDestination());
                trip.setDistance(calculatedDistance);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors du calcul de la distance : " + e.getMessage());
            }

            // Sauvegarder et retourner le trajet mis à jour
            Trip updatedTripInDb = tripRepository.save(trip);

            // Envoi d'un email au conducteur après la mise à jour du trajet
            try {
                emailService.sendEmail(updatedTripInDb);
            } catch (IOException e) {
                System.out.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            }

            return updatedTripInDb;
        }).orElseThrow(() -> new RuntimeException("Trip not found"));
    }

    /**
     * Supprime un trajet et remet les statuts du conducteur et du véhicule associés à "Disponible".
     * 
     * @param id Identifiant du trajet à supprimer.
     */
    public void deleteTrip(int id) {
        // Recherche du trajet à supprimer
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));

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

    /**
     * Recherche des trajets en fonction de l'origine ou de la destination.
     * 
     * @param origin Ville d'origine.
     * @param destination Ville de destination.
     * @return Liste des trajets correspondants aux critères.
     */
    public List<Trip> searchTrips(String origin, String destination) {
        return tripRepository.findByOriginContainingOrDestinationContaining(origin, destination);
    }
}
