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

@RestController
@RequestMapping("/users")
public class UtilisateurController {
    private final UtilisateurService userService;

    public UtilisateurController(UtilisateurService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUser")
    public Utilisateur getUser(@RequestParam String username) {
        System.out.println("On récupère qui est connecté, username: " + username);
        return userService.findByUsername(username);
    }

    // Création d'un utilisateur
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur createdUser = userService.saveUser(utilisateur);  // Essayer d'enregistrer l'utilisateur
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);  // Retourner l'utilisateur créé avec un code 201
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  // Si le nom d'utilisateur existe déjà, retourner une erreur 400
        }
    }

    // Connexion de l'utilisateur
    @PostMapping("/login")
    public boolean login(@RequestBody Utilisateur utilisateur) {
        System.out.println("username: " + utilisateur.getUsername());
        System.out.println("password: " + utilisateur.getPassword());
        return userService.authenticate(utilisateur.getUsername(), utilisateur.getPassword());  // Vérification des identifiants
    }
}
