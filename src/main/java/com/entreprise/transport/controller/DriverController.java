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

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.service.DriverService;

//Controllers
@RestController
@RequestMapping("/drivers")
public class DriverController {
	private final DriverService driverService;

	public DriverController(DriverService driverService) {
		this.driverService = driverService;
	}

	@GetMapping
	public List<Driver> getAllDrivers() {
		return driverService.findAllDrivers();
	}

	@PostMapping
	public Driver createDriver(@RequestBody Driver driver) {
		return driverService.saveDriver(driver);
	}

	@PutMapping("/{id}")
	public Driver updateDriver(@PathVariable int id, @RequestBody Driver driver) {
		return driverService.updateDriver(id, driver);
	}
	

	@DeleteMapping("/{id}")
	public void deleteDriver(@PathVariable int id) {
		driverService.deleteDriver(id);
	}

	@GetMapping("/search")
	public List<Driver> searchDrivers(@RequestParam String name) {
		return driverService.searchDrivers(name);
	}
}