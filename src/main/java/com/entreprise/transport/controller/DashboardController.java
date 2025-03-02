package com.entreprise.transport.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.transport.service.DashboardService;

/**
 * Contrôleur REST pour les statistiques du tableau de bord.
 * 
 * Ce contrôleur permet de récupérer les statistiques du tableau de bord, telles
 * que le nombre total de conducteurs, de véhicules et de trajets.
 * 
 * Auteur: Ouagal Mahamat
 */

@RestController
@RequestMapping("/dashboard")  // L'URL de base pour accéder aux statistiques du tableau de bord
public class DashboardController {
    private final DashboardService dashboardService;

    
	/**
	 * Constructeur pour l'injection de dépendance.
	 * 
	 * @param dashboardService Le service pour la gestion des statistiques du
	 *                         tableau de bord.
	 */
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Récupère les statistiques du tableau de bord.
     * 
     * @return Un ensemble de statistiques sur les conducteurs, véhicules et trajets.
     */
    @GetMapping("/stats")
    public Map<String, Long> getStatistics() {
        // Création d'un objet Map pour stocker les statistiques
        Map<String, Long> stats = new HashMap<>();
        
        // Récupération des statistiques via le service dashboardService
        stats.put("totalDrivers", dashboardService.getTotalDrivers());  // Total des conducteurs
        stats.put("totalVehicles", dashboardService.getTotalVehicles());  // Total des véhicules
        stats.put("totalTrips", dashboardService.getTotalTrips());  // Total des trajets
        
        // Retourne les statistiques sous forme de Map
        return stats;
    }
}
