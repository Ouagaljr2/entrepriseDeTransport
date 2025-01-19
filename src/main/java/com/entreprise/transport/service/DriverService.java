package com.entreprise.transport.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.repository.DriverRepository;

//Services
@Service
public class DriverService {
	private final DriverRepository driverRepository;

	public DriverService(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	public List<Driver> findAllDrivers() {
		return driverRepository.findAll();
	}

	public Driver saveDriver(Driver driver) {
		System.out.println("Saving driver: " + driver);
		return driverRepository.save(driver);
	}

	public Driver updateDriver(int id, Driver updatedDriver) {
		System.out.println("Updating driver with id: " + id);
		return driverRepository.findById(id).map(driver -> {
			driver.setName(updatedDriver.getName());
			driver.setLicenseNumber(updatedDriver.getLicenseNumber());
			driver.setPhoneNumber(updatedDriver.getPhoneNumber());
			return driverRepository.save(driver);
		}).orElseThrow(() -> new RuntimeException("Driver not found"));
	}

	public void deleteDriver(int id) {
		System.out.println("Deleting driver with id: " + id);
		driverRepository.deleteById(id);
	}

	public List<Driver> searchDrivers(String name) {
		System.out.println("Searching drivers with name: " + name);
		return driverRepository.findByNameContaining(name);
	}
}