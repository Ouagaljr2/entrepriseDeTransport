package com.entreprise.transport.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.TripRepository;
import com.entreprise.transport.repository.VehicleRepository;
import com.entreprise.transport.service.DriverService;

class DriverServiceTest {

	@Mock
	private DriverRepository driverRepository;

	@Mock
	private TripRepository tripRepository;

	@Mock
	private VehicleRepository vehicleRepository;

	@InjectMocks
	private DriverService driverService;

	private Driver driver;
	private Trip trip;
	private Vehicle vehicle;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		driver = new Driver();
		driver.setId(1);
		driver.setName("John Doe");
		driver.setLicenseNumber("AB123456");
		driver.setPhoneNumber("123-456-7890");
		driver.setEmail("johndoe@example.com");
		driver.setStatus("Disponible");

		vehicle = new Vehicle();
		vehicle.setId(1);
		vehicle.setModel("Toyota Corolla");
		vehicle.setRegistrationNumber("123XYZ");
		vehicle.setStatus("Disponible");

		trip = new Trip();
		trip.setId(1);
		trip.setDriver(driver);
		trip.setVehicle(vehicle);
		trip.setDistance(500);
		trip.setStatus("En cours");
		trip.setDate(LocalDate.now());
		trip.setDestination("Lyon");
		trip.setOrigin("Paris");

	}

	@Test
	void testSaveDriver() {
		when(driverRepository.save(driver)).thenReturn(driver);

		Driver savedDriver = driverService.saveDriver(driver);

		assertNotNull(savedDriver);
		assertEquals(driver.getName(), savedDriver.getName());
		verify(driverRepository, times(1)).save(driver);
	}

	@Test
	void testUpdateDriver() {
		when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));
		when(driverRepository.save(driver)).thenReturn(driver);

		driver.setName("John Updated");
		Driver updatedDriver = driverService.updateDriver(driver.getId(), driver);

		assertEquals("John Updated", updatedDriver.getName());
		verify(driverRepository, times(1)).save(driver);
	}

	@Test
	void testDeleteDriver() {
		when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));
		when(tripRepository.findByDriverId(driver.getId())).thenReturn(Arrays.asList(trip));
		when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

		driverService.deleteDriver(driver.getId());

		verify(tripRepository, times(1)).deleteAll(Arrays.asList(trip));
		verify(driverRepository, times(1)).deleteById(driver.getId());
		verify(vehicleRepository, times(1)).save(vehicle);
	}

	@Test
	void testSearchDrivers() {
		when(driverRepository.findByNameContaining("John")).thenReturn(Arrays.asList(driver));

		var result = driverService.searchDrivers("John");

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("John Doe", result.get(0).getName());
	}
}
