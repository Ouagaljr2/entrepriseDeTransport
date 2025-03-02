package com.entreprise.transport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.transport.model.Utilisateur;
import com.entreprise.transport.service.UtilisateurService;

/**
 * Contrôleur REST pour les utilisateurs.
 * 
 * Ce contrôleur permet de gérer les requêtes HTTP relatives aux utilisateurs.
 * Il permet de récupérer, enregistrer et authentifier des utilisateurs.
 * 
 * Auteur: Ouagal Mahamat
 */

@RestController
@RequestMapping("/users") // URL de base pour ce contrôleur
public class UtilisateurController {
    private final UtilisateurService userService;

    // Injection de dépendance pour UtilisateurService via le constructeur
	/**
	 * Constructeur pour l'injection de dépendance.
	 * 
	 * @param userService Le service pour la gestion des utilisateurs.
	 */
    public UtilisateurController(UtilisateurService userService) {
        this.userService = userService;
    }

    /**
     * Récupère un utilisateur en fonction de son nom d'utilisateur.
     * 
     * @param username Le nom d'utilisateur à rechercher.
     * @return L'utilisateur correspondant au nom d'utilisateur.
     */
    @GetMapping("/getUser")
    public Utilisateur getUser(@RequestParam String username) {
        return userService.findByUsername(username);
    }

    /**
     * Crée un nouvel utilisateur.
     * 
     * @param utilisateur L'utilisateur à créer, passé dans le corps de la requête.
     * @return Une réponse HTTP contenant l'utilisateur créé ou une erreur si
     *         l'utilisateur existe déjà.
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur createdUser = userService.saveUser(utilisateur);  // Essayer d'enregistrer l'utilisateur
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);  // Retourner l'utilisateur créé avec un code 201
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  // Si le nom d'utilisateur existe déjà, retourner une erreur 400
        }
    }

    /**
     * Permet à un utilisateur de se connecter.
     * 
     * @param utilisateur L'utilisateur avec ses identifiants (nom d'utilisateur et
     *                   mot de passe).
     * @return true si l'authentification est réussie, sinon false.
     */
    @PostMapping("/login")
    public boolean login(@RequestBody Utilisateur utilisateur) {
        return userService.authenticate(utilisateur.getUsername(), utilisateur.getPassword());  // Vérification des identifiants
    }
}
