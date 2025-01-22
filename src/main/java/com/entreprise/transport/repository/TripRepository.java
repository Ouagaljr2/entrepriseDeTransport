package com.entreprise.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.transport.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
	List<Trip> findByOriginContainingOrDestinationContaining(String origin, String destination);
	List<Trip> findByVehicleId(int id);
	List<Trip> findByDriverId(int id);
}