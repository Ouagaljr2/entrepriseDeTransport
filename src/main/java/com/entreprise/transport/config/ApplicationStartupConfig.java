package com.entreprise.transport.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.entreprise.transport.model.Utilisateur;
import com.entreprise.transport.service.UtilisateurService;

@Configuration
public class ApplicationStartupConfig {

    private final UtilisateurService utilisateurService;

    public ApplicationStartupConfig(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
            // Vérifie si un utilisateur avec un nom d'utilisateur spécifique existe déjà
            if (utilisateurService.findByUsername("admin") == null) {
                // Crée un utilisateur si non existant
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setUsername("admin");
                utilisateur.setPassword("admin123"); // Assure-toi que le mot de passe est encodé correctement
                utilisateur.setRole("ADMIN");

                // Enregistre l'utilisateur
                utilisateurService.saveUser(utilisateur);
                System.out.println("Utilisateur admin ajouté.");
            }
        };
    }
}
