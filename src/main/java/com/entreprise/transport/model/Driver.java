package com.entreprise.transport.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Représente un conducteur dans le système de gestion de transport.
 */
@Entity
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identifiant unique du conducteur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nom du conducteur.
     */
    private String name;
    
    /**
     * Numéro de licence du conducteur.
     */
    private String licenseNumber;
    
    /**
     * Numéro de téléphone du conducteur.
     */
    private String phoneNumber;
    
    /**
     * Adresse e-mail du conducteur.
     */
    private String email;
    
    /**
     * Statut du conducteur (actif/inactif, par exemple).
     */
    private String status;

    /**
     * Constructeur par défaut.
     */
    public Driver() {
    }

    /**
     * Retourne l'adresse e-mail du conducteur.
     * 
     * @return email du conducteur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse e-mail du conducteur.
     * 
     * @param email Nouvelle adresse e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le statut du conducteur.
     * 
     * @return statut du conducteur
     */
    public String getStatus() {
        return status;
    }

    /**
     * Définit le statut du conducteur.
     * 
     * @param status Nouveau statut
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retourne l'identifiant du conducteur.
     * 
     * @return identifiant unique
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant du conducteur.
     * 
     * @param id Nouvel identifiant
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom du conducteur.
     * 
     * @return nom du conducteur
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du conducteur.
     * 
     * @param driverName Nouveau nom
     */
    public void setName(String driverName) {
        this.name = driverName;
    }

    /**
     * Retourne le numéro de licence du conducteur.
     * 
     * @return numéro de licence
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Définit le numéro de licence du conducteur.
     * 
     * @param licenseNumber Nouveau numéro de licence
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * Retourne le numéro de téléphone du conducteur.
     * 
     * @return numéro de téléphone
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Définit le numéro de téléphone du conducteur.
     * 
     * @param phoneNumber Nouveau numéro de téléphone
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Génère un hashcode basé sur les attributs du conducteur.
     * 
     * @return valeur de hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, id, licenseNumber, name, phoneNumber, status);
    }

    /**
     * Compare deux objets Driver pour vérifier leur égalité.
     * 
     * @param obj Objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Driver)) {
            return false;
        }
        Driver other = (Driver) obj;
        return Objects.equals(email, other.email) && id == other.id
                && Objects.equals(licenseNumber, other.licenseNumber) && Objects.equals(name, other.name)
                && Objects.equals(phoneNumber, other.phoneNumber) && Objects.equals(status, other.status);
    }
}
