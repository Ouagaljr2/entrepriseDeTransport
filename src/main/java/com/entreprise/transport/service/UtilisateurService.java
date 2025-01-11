package com.entreprise.transport.service;

import org.springframework.stereotype.Service;

import com.entreprise.transport.model.Utilisateur;
import com.entreprise.transport.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
    private final UtilisateurRepository userRepository;

    public UtilisateurService(UtilisateurRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Utilisateur findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Utilisateur saveUser(Utilisateur user) {
        return userRepository.save(user);
    }
}