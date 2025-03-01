package com.entreprise.transport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Désactiver la protection CSRF pour tous les endpoints
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll() // Permet l'accès à tous les endpoints sans
																				// authentification
				);
		return http.build();
	}

	@Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Autoriser CORS sur toutes les URL
				.allowedOrigins("http://localhost:8081","http://localhost:3000") // Frontend React
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes autorisées
				.allowedHeaders("*") // Autoriser tous les en-têtes
				.allowCredentials(true); // Autoriser les cookies / authentification
	}

}
