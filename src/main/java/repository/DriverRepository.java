package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByNameContaining(String name);
}