package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByOriginContainingOrDestinationContaining(String origin, String destination);
}