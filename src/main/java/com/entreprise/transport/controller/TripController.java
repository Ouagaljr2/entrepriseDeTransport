package com.entreprise.transport.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.transport.model.Trip;
import com.entreprise.transport.service.TripService;

/***
 * Contrôleur REST pour les voyages.
 * 
 * Ce contrôleur permet de gérer les requêtes HTTP relatives aux voyages. Il
 * permet de récupérer, enregistrer, mettre à jour et supprimer des voyages.
 * 
 * Auteur: Ouagal Mahamat
 */

@RestController
@RequestMapping("/trips") // URL de base pour les voyages (trips)
public class TripController {
    private final TripService tripService;

    // Injection de dépendance pour TripService via le constructeur
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * Récupère tous les voyages.
     * 
     * @return La liste de tous les voyages enregistrés.
     */
    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.findAllTrips();
    }

    /**
     * Crée un nouveau voyage.
     * 
     * @param driverId L'ID du conducteur associé au voyage.
     * @param vehicleId L'ID du véhicule associé au voyage.
     * @param trip L'objet `Trip` qui contient les informations du voyage à créer.
     * @return Le voyage créé.
     */
    @PostMapping
    public Trip createTrip(@RequestParam int driverId, @RequestParam int vehicleId, @RequestBody Trip trip) {
        System.out.println("Creating trip: " + trip.toString());
        return tripService.saveTrip(driverId, vehicleId, trip);
    }

    /**
     * Met à jour un voyage existant.
     * 
     * @param id L'ID du voyage à mettre à jour.
     * @param trip L'objet `Trip` contenant les nouvelles informations du voyage.
     * @return Le voyage mis à jour.
     */
    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable int id, @RequestBody Trip trip) {
        return tripService.updateTrip(id, trip);
    }

    /**
     * Supprime un voyage existant.
     * 
     * @param id L'ID du voyage à supprimer.
     */
    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable int id) {
        tripService.deleteTrip(id);
    }

    /**
     * Recherche des voyages en fonction de l'origine et de la destination.
     * 
     * @param origin Le point de départ du voyage.
     * @param destination Le point d'arrivée du voyage.
     * @return La liste des voyages qui correspondent aux critères de recherche.
     */
    @GetMapping("/search")
    public List<Trip> searchTrips(@RequestParam String origin, @RequestParam String destination) {
        return tripService.searchTrips(origin, destination);
    }
}
