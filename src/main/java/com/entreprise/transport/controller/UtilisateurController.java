package com.entreprise.transport.controller;

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
    	System.out.println(" on recupere qui est connecté username: "+username);
		return userService.findByUsername(username);
	}

    // Création d'un utilisateur
    @PostMapping("/register")
    public Utilisateur createUser(@RequestBody Utilisateur utilisateur) {
        return userService.saveUser(utilisateur);
    }

    // Connexion de l'utilisateur
    @PostMapping("/login")
    public boolean login(@RequestBody Utilisateur utilisateur) {
    	System.out.println("username: "+utilisateur.getUsername());
    	System.out.println("password: "+utilisateur.getPassword());
        return userService.authenticate(utilisateur.getUsername(), utilisateur.getPassword()); // Vérification des identifiants
    }
}
