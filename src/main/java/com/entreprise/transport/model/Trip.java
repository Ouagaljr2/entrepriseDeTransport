package com.entreprise.transport.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Représente un trajet effectué par un conducteur avec un véhicule.
 * Contient les informations de base telles que l'origine, la destination, la distance et le statut du trajet.
 * 
 * @author Ouagal Mahamat
 */
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Conducteur associé au trajet.
     */
    @ManyToOne
    private Driver driver;

    /**
     * Véhicule utilisé pour le trajet.
     */
    @ManyToOne
    private Vehicle vehicle;

    /**
     * Lieu d'origine du trajet.
     */
    private String origin;

    /**
     * Lieu de destination du trajet.
     */
    private String destination;

    /**
     * Distance parcourue en kilomètres.
     */
    private double distance;

    /**
     * Date du trajet.
     */
    private LocalDate date;

    /**
     * Statut du trajet (en cours, terminé, annulé, etc.).
     */
    private String status;

    /**
     * Constructeur par défaut.
     * Initialise un trajet sans spécifier de valeurs.
     */
    public Trip() {
    }

    /**
     * Retourne l'identifiant unique du trajet.
     *
     * @return L'identifiant du trajet.
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du trajet.
     *
     * @param id L'identifiant à attribuer au trajet.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le conducteur associé au trajet.
     *
     * @return Le conducteur du trajet.
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Définit le conducteur associé au trajet.
     *
     * @param driver Le conducteur à attribuer au trajet.
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * Retourne le véhicule utilisé pour le trajet.
     *
     * @return Le véhicule du trajet.
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Définit le véhicule utilisé pour le trajet.
     *
     * @param vehicle Le véhicule à attribuer au trajet.
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Retourne le lieu d'origine du trajet.
     *
     * @return Le lieu d'origine du trajet.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Définit le lieu d'origine du trajet.
     *
     * @param origin Le lieu d'origine à attribuer au trajet.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Retourne le lieu de destination du trajet.
     *
     * @return Le lieu de destination du trajet.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Définit le lieu de destination du trajet.
     *
     * @param destination Le lieu de destination à attribuer au trajet.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Retourne la distance parcourue durant le trajet.
     *
     * @return La distance en kilomètres du trajet.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Définit la distance parcourue durant le trajet.
     *
     * @param distance La distance à attribuer au trajet.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Retourne la date du trajet.
     *
     * @return La date du trajet.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Définit la date du trajet.
     *
     * @param date La date à attribuer au trajet.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Retourne le statut actuel du trajet.
     * Le statut peut être "en cours", "terminé", "annulé", etc.
     *
     * @return Le statut du trajet.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Définit le statut du trajet.
     *
     * @param status Le statut à attribuer au trajet.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Calcule le code de hachage de l'objet `Trip` en utilisant les attributs pertinents.
     *
     * @return Le code de hachage de l'objet `Trip`.
     */
    @Override
    public int hashCode() {
        return Objects.hash(date, destination, distance, driver, id, origin, status, vehicle);
    }

    /**
     * Compare cet objet `Trip` à un autre objet pour vérifier s'ils sont égaux.
     * L'égalité est définie par la correspondance des attributs : 
     * date, destination, distance, conducteur, identifiant, origine, statut et véhicule.
     *
     * @param obj L'objet à comparer.
     * @return true si l'objet est égal à cet objet `Trip`, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Trip)) {
            return false;
        }
        Trip other = (Trip) obj;
        return Objects.equals(date, other.date) && Objects.equals(destination, other.destination)
                && Double.doubleToLongBits(distance) == Double.doubleToLongBits(other.distance)
                && Objects.equals(driver, other.driver) && id == other.id && Objects.equals(origin, other.origin)
                && Objects.equals(status, other.status) && Objects.equals(vehicle, other.vehicle);
    }
}
