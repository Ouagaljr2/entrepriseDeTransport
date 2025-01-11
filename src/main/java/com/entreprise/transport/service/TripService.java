package com.entreprise.transport.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entreprise.transport.model.Trip;
import com.entreprise.transport.repository.TripRepository;


@Service
public class TripService {
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip updateTrip(int id, Trip updatedTrip) {
        return tripRepository.findById(id).map(trip -> {
            trip.setDriver(updatedTrip.getDriver());
            trip.setVehicle(updatedTrip.getVehicle());
            trip.setOrigin(updatedTrip.getOrigin());
            trip.setDestination(updatedTrip.getDestination());
            trip.setDistance(updatedTrip.getDistance());
            trip.setDate(updatedTrip.getDate());
            trip.setStatus(updatedTrip.getStatus());
            return tripRepository.save(trip);
        }).orElseThrow(() -> new RuntimeException("Trip not found"));
    }

    public void deleteTrip(int id) {
        tripRepository.deleteById(id);
    }

    public List<Trip> searchTrips(String origin, String destination) {
        return tripRepository.findByOriginContainingOrDestinationContaining(origin, destination);
    }
}