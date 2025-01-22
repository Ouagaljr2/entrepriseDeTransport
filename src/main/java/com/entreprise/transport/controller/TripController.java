package com.entreprise.transport.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.transport.model.Trip;
import com.entreprise.transport.service.TripService;

@RestController
@RequestMapping("/trips")
public class TripController {
	private final TripService tripService;

	public TripController(TripService tripService) {
		this.tripService = tripService;
	}

	@GetMapping
	public List<Trip> getAllTrips() {
		return tripService.findAllTrips();
	}

	@PostMapping
	public Trip createTrip(@RequestParam int driverId, @RequestParam int vehicleId, @RequestBody Trip trip) {
		System.out.println("Creating trip: " + trip.toString());
		return tripService.saveTrip(driverId, vehicleId, trip);
	}

	@PutMapping("/{id}")
	public Trip updateTrip(@PathVariable int id, @RequestBody Trip trip) {
		return tripService.updateTrip(id, trip);
	}

	@DeleteMapping("/{id}")
	public void deleteTrip(@PathVariable int id) {
		tripService.deleteTrip(id);
	}

	@GetMapping("/search")
	public List<Trip> searchTrips(@RequestParam String origin, @RequestParam String destination) {
		return tripService.searchTrips(origin, destination);
	}
}