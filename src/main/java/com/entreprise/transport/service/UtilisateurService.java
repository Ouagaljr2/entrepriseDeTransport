package com.entreprise.transport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entreprise.transport.model.Utilisateur;
import com.entreprise.transport.repository.UtilisateurRepository;

/**
 * Service pour gérer les utilisateurs dans l'application.
 * 
 * Ce service permet de rechercher, enregistrer et authentifier les
 * utilisateurs.
 * 
 * Auteur: Ouagal Mahamat
 */
@Service
public class UtilisateurService {

    // Injection de dépendance pour le repository des utilisateurs
    @Autowired
    private final UtilisateurRepository userRepository;

    // Injection de dépendance pour l'encodeur de mot de passe
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Constructeur pour initialiser les dépendances.
	 * 
	 * @param userRepository  Le repository pour les utilisateurs
	 * @param passwordEncoder L'encodeur de mot de passe
	 */
    public UtilisateurService(UtilisateurRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

	/**
	 * Méthode pour rechercher un utilisateur par son nom d'utilisateur.
	 * 
	 * @param username Le nom d'utilisateur de l'utilisateur à rechercher
	 * @return Utilisateur L'utilisateur trouvé, ou null s'il n'existe pas
	 */
    public Utilisateur findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Méthode pour enregistrer un nouvel utilisateur. 
     * Avant d'enregistrer, vérifie si l'utilisateur existe déjà avec le même nom d'utilisateur.
     * 
     * @param user L'utilisateur à enregistrer
     * @return Utilisateur L'utilisateur enregistré
     * @throws UsernameNotFoundException Si le nom d'utilisateur existe déjà
     */
    public Utilisateur saveUser(Utilisateur user) throws UsernameNotFoundException {
        // Vérification si un utilisateur avec le même nom d'utilisateur existe déjà
        if (userRepository.findByUsername(user.getUsername()) != null) {

            // Si l'utilisateur existe déjà, une exception est levée
            throw new UsernameNotFoundException("Le nom d'utilisateur existe déjà.");
        }
        
        // Si l'utilisateur n'existe pas, on encode son mot de passe et on l'enregistre
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Méthode pour authentifier un utilisateur en vérifiant si le mot de passe correspond à celui de l'utilisateur dans la base de données.
     * 
     * @param username Le nom d'utilisateur de l'utilisateur à authentifier
     * @param password Le mot de passe fourni pour l'authentification
     * @return boolean Retourne true si l'authentification est réussie, false sinon
     */
    public boolean authenticate(String username, String password) {
        // Recherche de l'utilisateur par son nom d'utilisateur
        Utilisateur user = userRepository.findByUsername(username);
        
        // Si l'utilisateur existe, on compare les mots de passe
        if (user != null) {
            return bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        
        // Si l'utilisateur n'existe pas, l'authentification échoue
        return false;
    }
}
