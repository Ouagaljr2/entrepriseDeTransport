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

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.service.DriverService;

/**
 * Contrôleur REST pour les conducteurs.
 * 
 * Ce contrôleur permet de gérer les requêtes HTTP relatives aux conducteurs. Il
 * permet de récupérer, enregistrer, mettre à jour et supprimer des conducteurs.
 * 
 * Auteur: Ouagal Mahamat
 */

// Le contrôleur qui gère les conducteurs
@RestController
@RequestMapping("/drivers") // URL de base pour les conducteurs (drivers)
public class DriverController {
    private final DriverService driverService;

    // Injection de dépendance pour DriverService via le constructeur
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * Récupère tous les conducteurs.
     * 
     * @return La liste de tous les conducteurs enregistrés.
     */
    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.findAllDrivers();
    }

    /**
     * Crée un nouveau conducteur.
     * 
     * @param driver L'objet `Driver` qui contient les informations du conducteur à créer.
     * @return Le conducteur créé.
     */
    @PostMapping
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    /**
     * Met à jour un conducteur existant.
     * 
     * @param id L'ID du conducteur à mettre à jour.
     * @param driver L'objet `Driver` contenant les nouvelles informations du conducteur.
     * @return Le conducteur mis à jour.
     */
    @PutMapping("/{id}")
    public Driver updateDriver(@PathVariable int id, @RequestBody Driver driver) {
        return driverService.updateDriver(id, driver);
    }

    /**
     * Supprime un conducteur existant.
     * 
     * @param id L'ID du conducteur à supprimer.
     */
    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable int id) {
        driverService.deleteDriver(id);
    }

    /**
     * Recherche des conducteurs par leur nom.
     * 
     * @param name Le nom du conducteur à rechercher.
     * @return La liste des conducteurs qui correspondent au nom donné.
     */
    @GetMapping("/search")
    public List<Driver> searchDrivers(@RequestParam String name) {
        return driverService.searchDrivers(name);
    }
}
