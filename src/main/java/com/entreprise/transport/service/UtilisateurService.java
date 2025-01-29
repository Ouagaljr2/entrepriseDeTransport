package com.entreprise.transport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entreprise.transport.model.Utilisateur;
import com.entreprise.transport.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
	
	@Autowired
    private final UtilisateurRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UtilisateurService(UtilisateurRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    // Trouver un utilisateur par son nom d'utilisateur
    public Utilisateur findByUsername(String username) {
        System.out.println("username: " + username);
        return userRepository.findByUsername(username);
    }

    // Enregistrer un utilisateur après avoir encodé son mot de passe
    public Utilisateur saveUser(Utilisateur user) throws UsernameNotFoundException {
        System.out.println("user is beeing saved: " + user.getUsername());
        // Vérifier si le username existe déjà
        if (userRepository.findByUsername(user.getUsername()) != null) {
        	
            // Si l'utilisateur existe, on lève une exception personnalisée
            throw new UsernameNotFoundException("Le nom d'utilisateur existe déjà.");
        }
        // Sinon, on encode le mot de passe et on enregistre l'utilisateur
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("userpass: " + user.getPassword());
        return userRepository.save(user);
    }

    // Authentifier un utilisateur en comparant son nom d'utilisateur et son mot de passe
    public boolean authenticate(String username, String password) {
        Utilisateur user = userRepository.findByUsername(username);
        System.out.println("userpassebefore: " + user.getPassword());
        
        if (user != null) {
            return bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}