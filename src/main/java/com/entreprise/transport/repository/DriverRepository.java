
package com.entreprise.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.transport.model.Driver;

/**
 * Repository pour l'entité Driver. Cette interface permet d'effectuer des
 * opérations CRUD sur les conducteurs.
 *
 * Auteur: Ouagal Mahamat
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

	/**
	 * Recherche les conducteurs dont le nom contient la chaîne spécifiée.
	 *
	 * @param name la chaîne à rechercher dans les noms des conducteurs
	 * @return une liste de conducteurs dont le nom contient la chaîne spécifiée
	 */
	List<Driver> findByNameContaining(String name);
}
