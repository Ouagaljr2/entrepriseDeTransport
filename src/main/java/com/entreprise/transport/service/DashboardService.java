package com.entreprise.transport.service;

import org.springframework.stereotype.Service;

import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.TripRepository;
import com.entreprise.transport.repository.VehicleRepository;

@Service
public class DashboardService {
	private final DriverRepository driverRepository;
	private final VehicleRepository vehicleRepository;
	private final TripRepository tripRepository;

	public DashboardService(DriverRepository driverRepository, VehicleRepository vehicleRepository,
			TripRepository tripRepository) {
		this.driverRepository = driverRepository;
		this.vehicleRepository = vehicleRepository;
		this.tripRepository = tripRepository;
	}

	public long getTotalDrivers() {
		return driverRepository.count();
	}

	public long getTotalVehicles() {
		return vehicleRepository.count();
	}

	public long getTotalTrips() {
		return tripRepository.count();
	}
}