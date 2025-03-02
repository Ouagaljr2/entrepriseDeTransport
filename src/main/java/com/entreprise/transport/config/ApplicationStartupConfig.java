package com.entreprise.transport.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.entreprise.transport.model.Utilisateur;
import com.entreprise.transport.service.UtilisateurService;

/**
 * Configuration de démarrage de l'application.
 * <p>
 * Cette classe est responsable de l'initialisation d'un utilisateur de type "admin"
 * lors du démarrage de l'application si aucun utilisateur avec ce nom n'existe déjà dans la base de données.
 * Elle utilise le {@link CommandLineRunner} de Spring Boot pour exécuter du code au démarrage de l'application.
 * </p>
 */
@Configuration
public class ApplicationStartupConfig {

    private final UtilisateurService utilisateurService;

    /**
     * Constructeur pour initialiser le service Utilisateur.
     *
     * @param utilisateurService Le service pour la gestion des utilisateurs
     */
    public ApplicationStartupConfig(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    

    /**
     * Exécute du code au démarrage de l'application.
     * <p>
     * Cette méthode vérifie si un utilisateur avec le nom d'utilisateur "admin" existe déjà dans la base de données.
     * Si l'utilisateur n'existe pas, il est créé avec le nom d'utilisateur "admin", le mot de passe "admin" et le rôle "Admin".
     * Le mot de passe doit être encodé correctement avant d'être sauvegardé (cela n'est pas pris en charge ici pour des raisons de simplicité).
     * </p>
     * 
     * @return Un {@link CommandLineRunner} qui est exécuté lors du démarrage de l'application.
     */
    @Bean
    public CommandLineRunner run() {
        return args -> {
            // Vérifie si un utilisateur avec un nom d'utilisateur spécifique existe déjà
            if (utilisateurService.findByUsername("admin") == null) {
                // Crée un utilisateur si non existant
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setUsername("admin");
                utilisateur.setPassword("admin"); // Assure-toi que le mot de passe est encodé correctement
                utilisateur.setRole("Admin");
                // Enregistre l'utilisateur
                utilisateurService.saveUser(utilisateur);
            }

        };
    }
}
