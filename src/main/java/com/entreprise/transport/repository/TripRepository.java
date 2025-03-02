
package com.entreprise.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.transport.model.Trip;

/**
 * Repository pour l'entité Trip. Cette interface permet d'effectuer des
 * opérations CRUD sur les trajets.
 *
 * Auteur: Ouagal Mahamat
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	/**
	 * Recherche les trajets dont l'origine ou la destination contient la chaîne
	 * spécifiée.
	 *
	 * @param origin      la chaîne à rechercher dans les origines des trajets
	 * @param destination la chaîne à rechercher dans les destinations des trajets
	 * @return une liste de trajets dont l'origine ou la destination contient la
	 *         chaîne spécifiée
	 */
	List<Trip> findByOriginContainingOrDestinationContaining(String origin, String destination);

	/**
	 * Recherche les trajets par identifiant de véhicule.
	 *
	 * @param id l'identifiant du véhicule
	 * @return une liste de trajets associés à l'identifiant de véhicule spécifié
	 */
	List<Trip> findByVehicleId(int id);

	/**
	 * Recherche les trajets par identifiant de conducteur.
	 *
	 * @param id l'identifiant du conducteur
	 * @return une liste de trajets associés à l'identifiant de conducteur spécifié
	 */
	List<Trip> findByDriverId(int id);
}
