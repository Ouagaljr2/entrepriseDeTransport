/**
 * Représente un trajet effectué par un conducteur avec un véhicule.
 * Contient les informations de base telles que l'origine, la destination, la distance et le statut du trajet.
 * 
 * @author Ouagal Mahamat
 */
package com.entreprise.transport.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Classe représentant un trajet dans le système de gestion de transport.
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
     */
    public Trip() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, destination, distance, driver, id, origin, status, vehicle);
    }

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
