package com.entreprise.transport.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.VehicleRepository;
import com.entreprise.transport.repository.TripRepository;

@Service
public class TripService {
	private final TripRepository tripRepository;
	private final DriverRepository driverRepository;
	private final VehicleRepository vehicleRepository;
	private final EmailService emailService;
	private final Distance distanceService;

	// Injection des dépendances
	public TripService(TripRepository tripRepository, DriverRepository driverRepository,
			VehicleRepository vehicleRepository, EmailService emailService, Distance distance) {
		this.tripRepository = tripRepository;
		this.driverRepository = driverRepository;
		this.vehicleRepository = vehicleRepository;
		this.emailService = emailService;
		this.distanceService = distance;
	}

	public List<Trip> findAllTrips() {
		return tripRepository.findAll();
	}

	@Transactional
	public Trip saveTrip(int driverId, int vehicleId, Trip trip) {
		// Recherche des entités Driver et Vehicle par leurs ID
		Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));
		Vehicle vehicle = vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new RuntimeException("Vehicle not found"));

		// Mise à jour du statut des entités Driver et Vehicle à "Non disponible"
		driver.setStatus("Non disponible");
		vehicle.setStatus("Non disponible");

		// Sauvegarde des entités mises à jour
		driverRepository.save(driver);
		vehicleRepository.save(vehicle);

		// Calcul de la distance entre les deux villes (origine et destination)
		try {
			String origin = trip.getOrigin(); // Exemple : "Paris"
			String destination = trip.getDestination(); // Exemple : "Lyon"

			double distance = distanceService.calculateDistance(origin, destination);
			System.out.println("Distance entre " + origin + " et " + destination + " : " + distance + " km");
			trip.setDistance(distance); // Ajouter la distance au Trip
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors du calcul de la distance : " + e.getMessage());
		}

		// Affectation des entités Driver et Vehicle au Trip
		trip.setDriver(driver);
		trip.setVehicle(vehicle);

		// Sauvegarde du Trip dans la base de données
		Trip savedTrip = tripRepository.save(trip);

		// Envoi d'un email générique au conducteur avec les détails du trajet
		try {
			if (driver.getEmail() != null && !driver.getEmail().isEmpty()) {
				emailService.sendEmail(savedTrip); // Envoi de l'email au conducteur avec origine et destination
			} else {
				System.out.println("Email du conducteur non disponible");
			}
		} catch (IOException e) {
			System.out.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
		}

		return savedTrip;
	}

	@Transactional
	public Trip updateTrip(int id, Trip updatedTrip) {

		return tripRepository.findById(id).map(trip -> {
			// Conserver l'ancien conducteur
			Driver oldDriver = trip.getDriver();
			if (oldDriver != null) {
				System.out.println("Ancien conducteur conservé: " + oldDriver.getEmail());
				trip.setDriver(oldDriver);
			}

			// Conserver l'ancien véhicule
			Vehicle oldVehicle = trip.getVehicle();
			if (oldVehicle != null) {
				System.out.println("Ancien véhicule conservé: " + oldVehicle.getRegistrationNumber());
				trip.setVehicle(oldVehicle);
			}

			// Mettre à jour uniquement les autres champs du trajet
			trip.setOrigin(updatedTrip.getOrigin());
			trip.setDestination(updatedTrip.getDestination());
			trip.setDate(updatedTrip.getDate());
			trip.setStatus(updatedTrip.getStatus());

			// Calculer la distance avant la mise à jour
			try {
				double calculatedDistance = distanceService.calculateDistance(updatedTrip.getOrigin(),
						updatedTrip.getDestination());
				trip.setDistance(calculatedDistance);
				System.out.println("Distance calculée : " + calculatedDistance);
			} catch (Exception e) {
				throw new RuntimeException("Erreur lors du calcul de la distance : " + e.getMessage());
			}

			// Sauvegarder et retourner le trajet mis à jour
			Trip updatedTripInDb = tripRepository.save(trip);

			// Envoi d'un email au conducteur après la mise à jour du trajet
			try {
				System.out.println("Envoi d'un email au conducteur après la mise à jour du trajet");
				emailService.sendEmail(updatedTripInDb);
			} catch (IOException e) {
				System.out.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
			}
			System.out.println("distance a donée: " + updatedTripInDb.getDistance());

			return updatedTripInDb;
		}).orElseThrow(() -> new RuntimeException("Trip not found"));
	}

	public void deleteTrip(int id) {
		// Recherche du trajet à supprimer
		Trip trip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));

		// Récupérer le conducteur et le véhicule associés au trajet
		Driver driver = trip.getDriver();
		Vehicle vehicle = trip.getVehicle();

		// Mise à jour du statut des entités Driver et Vehicle à "Disponible"
		driver.setStatus("Disponible");
		vehicle.setStatus("Disponible");

		// Sauvegarder les entités mises à jour
		driverRepository.save(driver);
		vehicleRepository.save(vehicle);

		// Suppression du trajet
		tripRepository.deleteById(id);
	}

	public List<Trip> searchTrips(String origin, String destination) {
		return tripRepository.findByOriginContainingOrDestinationContaining(origin, destination);
	}
}
