
package com.entreprise.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.transport.model.Vehicle;

/**
 * Repository for the Vehicle entity. This interface allows performing CRUD
 * operations on vehicles.
 *
 * Author: Ouagal Mahamat
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	/**
	 * Finds vehicles by their registration number containing the specified string.
	 *
	 * @param registrationNumber the string to search for in the registration
	 *                           numbers
	 * @return a list of vehicles whose registration number contains the specified
	 *         string
	 */
	List<Vehicle> findByRegistrationNumberContaining(String registrationNumber);
}
