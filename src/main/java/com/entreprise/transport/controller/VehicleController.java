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

import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.service.VehicleService;

/**
 * Contrôleur REST pour les véhicules.
 * 
 * Ce contrôleur permet de gérer les requêtes HTTP relatives aux véhicules. Il
 * permet de récupérer, enregistrer, mettre à jour et supprimer des véhicules.
 * 
 * Auteur: Ouagal Mahamat
 */

@RestController
@RequestMapping("/vehicles") // URL de base pour ce contrôleur
public class VehicleController {
	private final VehicleService vehicleService;

	// Injection de dépendance pour VehicleService via le constructeur
	/**
	 * Constructeur pour l'injection de dépendance.
	 * 
	 * @param vehicleService Le service pour la gestion des véhicules.
	 */
	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * Récupère tous les véhicules.
	 * 
	 * @return Liste des véhicules.
	 */
	@GetMapping
	public List<Vehicle> getAllVehicles() {
		return vehicleService.findAllVehicles();
	}

	/**
	 * Crée un nouveau véhicule.
	 * 
	 * @param vehicle Le véhicule à créer, passé dans le corps de la requête.
	 * @return Le véhicule créé.
	 */
	@PostMapping
	public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
		return vehicleService.saveVehicle(vehicle);
	}

	/**
	 * Met à jour un véhicule existant en fonction de son ID.
	 * 
	 * @param id L'ID du véhicule à mettre à jour.
	 * @param vehicle Le véhicule avec les nouvelles informations.
	 * @return Le véhicule mis à jour.
	 */
	@PutMapping("/{id}")
	public Vehicle updateVehicle(@PathVariable int id, @RequestBody Vehicle vehicle) {
		return vehicleService.updateVehicle(id, vehicle);
	}

	/**
	 * Supprime un véhicule en fonction de son ID.
	 * 
	 * @param id L'ID du véhicule à supprimer.
	 */
	@DeleteMapping("/{id}")
	public void deleteVehicle(@PathVariable int id) {
		vehicleService.deleteVehicle(id);
	}

	/**
	 * Recherche un véhicule par son numéro d'immatriculation.
	 * 
	 * @param registrationNumber Le numéro d'immatriculation à rechercher.
	 * @return Liste des véhicules correspondant à ce numéro d'immatriculation.
	 */
	@GetMapping("/search")
	public List<Vehicle> searchVehicles(@RequestParam String registrationNumber){
		return vehicleService.searchVehicles(registrationNumber);
	}
}
