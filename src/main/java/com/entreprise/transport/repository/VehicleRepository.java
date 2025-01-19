package com.entreprise.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.transport.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	List<Vehicle> findByRegistrationNumberContaining(String registrationNumber);
}