package com.entreprise.transport.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping
	public Utilisateur createUser(@RequestBody Utilisateur utilsiateur) {
		return userService.saveUser(utilsiateur);
	}
}