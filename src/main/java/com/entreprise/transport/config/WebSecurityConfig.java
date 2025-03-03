
package com.entreprise.transport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration de la sécurité de l'application. Cette classe configure les
 * paramètres de sécurité pour l'application web.
 * Auteur: Ouagal Mahamat
 */
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	/**
	 * Configure la chaîne de filtres de sécurité.
	 * 
	 * @param http l'objet HttpSecurity à configurer
	 * @return l'objet SecurityFilterChain configuré
	 * @throws Exception si une erreur de configuration survient
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Désactiver la protection CSRF pour tous les endpoints
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll() // Permet l'accès à tous les endpoints sans
																				// authentification
				);
		return http.build();
	}

	/**
	 * Fournit un encodeur de mots de passe BCrypt.
	 * 
	 * @return un objet BCryptPasswordEncoder
	 */
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configure les mappings CORS pour l'application.
	 * 
	 * @param registry l'objet CorsRegistry à configurer
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Autoriser CORS sur toutes les URL
				.allowedOrigins("http://localhost:8081", "http://10.192.12.42","http://10.100.18.111") // Autoriser les origines spécifiques
																					// (Frontend React)
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes HTTP autorisées
				.allowedHeaders("*") // Autoriser tous les en-têtes
				.allowCredentials(true); // Autoriser les cookies / authentification
	}
}
