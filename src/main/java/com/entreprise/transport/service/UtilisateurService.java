package com.entreprise.transport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entreprise.transport.model.Utilisateur;
import com.entreprise.transport.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
	private final UtilisateurRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UtilisateurService(UtilisateurRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
	}

	// Trouver un utilisateur par son nom d'utilisateur
	public Utilisateur findByUsername(String username) {
		System.out.println("username: "+username);
		return userRepository.findByUsername(username);
	}

	// Enregistrer un utilisateur après avoir encodé son mot de passe
	public Utilisateur saveUser(Utilisateur user) {
		System.out.println("user: "+user);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // Encodage du mot de passe
		return userRepository.save(user);
	}

	// Authentifier un utilisateur en comparant son nom d'utilisateur et son mot de passe
	public boolean authenticate(String username, String password) {
		Utilisateur user = userRepository.findByUsername(username);
		if (user != null) {
			return bCryptPasswordEncoder.matches(password, user.getPassword()); // Comparaison du mot de passe
		}
		return false; // Authentification échouée
	}
}
