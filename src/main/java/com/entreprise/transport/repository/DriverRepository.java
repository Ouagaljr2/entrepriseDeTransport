package com.entreprise.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.transport.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
	List<Driver> findByNameContaining(String name);
}