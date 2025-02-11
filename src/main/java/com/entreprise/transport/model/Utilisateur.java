package com.entreprise.transport.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Classe représentant un utilisateur du système.
 * 
 * @author Ouagal Mahamat
 */
@Entity
public class Utilisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/** Nom d'utilisateur unique et obligatoire. */
	@Column(nullable = false, unique = true)
	private String username;

	/** Mot de passe de l'utilisateur (chiffré en production). */
	@Column(nullable = false)
	private String password;

	/** Rôle de l'utilisateur (ex: ADMIN, USER). */
	private String role;

	/**
	 * Constructeur par défaut.
	 */
	public Utilisateur() {
	}

	/**
	 * Obtient l'identifiant unique de l'utilisateur.
	 * 
	 * @return l'identifiant de l'utilisateur
	 */
	public int getId() {
		return id;
	}

	/**
	 * Définit l'identifiant unique de l'utilisateur.
	 * 
	 * @param id l'identifiant à définir
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtient le nom d'utilisateur.
	 * 
	 * @return le nom d'utilisateur
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Définit le nom d'utilisateur.
	 * 
	 * @param username le nom d'utilisateur à définir
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Obtient le mot de passe de l'utilisateur.
	 * 
	 * @return le mot de passe
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Définit le mot de passe de l'utilisateur.
	 * 
	 * @param password le mot de passe à définir
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtient le rôle de l'utilisateur.
	 * 
	 * @return le rôle de l'utilisateur
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Définit le rôle de l'utilisateur.
	 * 
	 * @param role le rôle à définir
	 */
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Utilisateur)) {
			return false;
		}
		Utilisateur other = (Utilisateur) obj;
		return id == other.id && Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(username, other.username);
	}
}
