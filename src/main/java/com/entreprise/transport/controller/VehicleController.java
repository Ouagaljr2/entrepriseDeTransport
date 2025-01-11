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

import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.service.VehicleService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
	private final VehicleService vehicleService;

	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	@GetMapping
	public List<Vehicle> getAllVehicles() {
		return vehicleService.findAllVehicles();
	}

	@PostMapping
	public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
		return vehicleService.saveVehicle(vehicle);
	}

	@PutMapping("/{id}")
	public Vehicle updateVehicle(@PathVariable int id, @RequestBody Vehicle vehicle) {
		return vehicleService.updateVehicle(id, vehicle);
	}

	@DeleteMapping("/{id}")
	public void deleteVehicle(@PathVariable int id) {
		vehicleService.deleteVehicle(id);
	}

	@GetMapping("/search")
	public List<Vehicle> searchVehicles(@RequestParam String model) {
		return vehicleService.searchVehicles(model);
	}
}