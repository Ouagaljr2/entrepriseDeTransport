package com.entreprise.transport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.transport.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    // Trouver un utilisateur par son nom d'utilisateur
	Utilisateur findByUsername(String username);	
	
	
}