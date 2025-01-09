package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByModelContaining(String model);
}