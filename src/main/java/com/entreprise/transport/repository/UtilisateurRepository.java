
package com.entreprise.transport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.transport.model.Utilisateur;

/**
 * Repository for the Utilisateur entity. This interface allows performing CRUD
 * operations on users.
 *
 * Author: Ouagal Mahamat
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

	/**
	 * Finds a user by their username.
	 *
	 * @param username the username to search for
	 * @return the user with the specified username
	 */
	Utilisateur findByUsername(String username);
}
