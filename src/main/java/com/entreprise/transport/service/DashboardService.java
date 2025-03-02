
package com.entreprise.transport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.TripRepository;
import com.entreprise.transport.repository.VehicleRepository;

/**
 * Service pour gérer les opérations du tableau de bord. Cette classe fournit
 * des méthodes pour obtenir des statistiques sur les conducteurs, les véhicules
 * et les trajets.
 *
 * Auteur: Ouagal Mahamat
 */
@Service
public class DashboardService {

	@Autowired
	private final DriverRepository driverRepository;

	@Autowired
	private final VehicleRepository vehicleRepository;

	@Autowired
	private final TripRepository tripRepository;

	/**
	 * Constructeur pour initialiser les repositories.
	 *
	 * @param driverRepository  le repository des conducteurs
	 * @param vehicleRepository le repository des véhicules
	 * @param tripRepository    le repository des trajets
	 */
	public DashboardService(DriverRepository driverRepository, VehicleRepository vehicleRepository,
			TripRepository tripRepository) {
		this.driverRepository = driverRepository;
		this.vehicleRepository = vehicleRepository;
		this.tripRepository = tripRepository;
	}

	/**
	 * Obtient le nombre total de conducteurs.
	 *
	 * @return le nombre total de conducteurs
	 */
	public long getTotalDrivers() {
		return driverRepository.count();
	}

	/**
	 * Obtient le nombre total de véhicules.
	 *
	 * @return le nombre total de véhicules
	 */
	public long getTotalVehicles() {
		return vehicleRepository.count();
	}

	/**
	 * Obtient le nombre total de trajets.
	 *
	 * @return le nombre total de trajets
	 */
	public long getTotalTrips() {
		return tripRepository.count();
	}
}
