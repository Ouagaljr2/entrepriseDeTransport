package com.entreprise.transport.serviceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.VehicleRepository;
import com.entreprise.transport.repository.TripRepository;
import com.entreprise.transport.service.EmailService;
import com.entreprise.transport.service.Distance;
import com.entreprise.transport.service.TripService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private Distance distanceService;

    private TripService tripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tripService = new TripService(tripRepository, driverRepository, vehicleRepository, emailService, distanceService);
    }

    @Test
    void testSaveTrip() throws IOException {
        // Arrange
        int driverId = 1;
        int vehicleId = 2;
        Trip trip = new Trip();
        trip.setOrigin("Paris");
        trip.setDestination("Lyon");

        Driver driver = new Driver();
        driver.setId(driverId);
        driver.setEmail("driver@example.com");

        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        try {
			when(distanceService.calculateDistance("Paris", "Lyon")).thenReturn(500.0);
		} catch (Exception e) {
			System.out.println("Error"+e.getMessage());
			e.printStackTrace();
		}
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        // Act
        Trip savedTrip = tripService.saveTrip(driverId, vehicleId, trip);

        // Assert
        assertNotNull(savedTrip);
        assertEquals(driver, savedTrip.getDriver());
        assertEquals(vehicle, savedTrip.getVehicle());
        verify(tripRepository).save(savedTrip);
        verify(emailService).sendEmail(savedTrip);
    }

    @Test
    void testUpdateTrip() throws IOException {
        // Arrange
        int tripId = 1;
        Trip existingTrip = new Trip();
        existingTrip.setId(tripId);
        existingTrip.setOrigin("Paris");
        existingTrip.setDestination("Lyon");

        Trip updatedTrip = new Trip();
        updatedTrip.setOrigin("Paris");
        updatedTrip.setDestination("Marseille");

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(existingTrip));
        try {
			when(distanceService.calculateDistance("Paris", "Marseille")).thenReturn(600.0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        when(tripRepository.save(any(Trip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Trip result = tripService.updateTrip(tripId, updatedTrip);

        // Assert
        assertNotNull(result);
        assertEquals("Paris", result.getOrigin());
        assertEquals("Marseille", result.getDestination());

        ArgumentCaptor<Trip> tripCaptor = ArgumentCaptor.forClass(Trip.class);
        verify(tripRepository).save(tripCaptor.capture());

        Trip savedTrip = tripCaptor.getValue();
        assertEquals("Paris", savedTrip.getOrigin());
        assertEquals("Marseille", savedTrip.getDestination());
    }


    @Test
    void testDeleteTrip() {
        // Arrange
        int tripId = 1;
        Trip trip = new Trip();
        trip.setId(tripId);
        Driver driver = new Driver();
        Vehicle vehicle = new Vehicle();
        trip.setDriver(driver);
        trip.setVehicle(vehicle);

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));

        // Act
        tripService.deleteTrip(tripId);

        // Assert
        verify(driverRepository).save(driver);
        verify(vehicleRepository).save(vehicle);
        verify(tripRepository).deleteById(tripId);
    }

    @Test
    void testSearchTrips() {
        // Arrange
        String origin = "Paris";
        String destination = "Lyon";
        Trip trip1 = new Trip();
        trip1.setOrigin("Paris");
        trip1.setDestination("Lyon");
        Trip trip2 = new Trip();
        trip2.setOrigin("Paris");
        trip2.setDestination("Marseille");

        when(tripRepository.findByOriginContainingOrDestinationContaining(origin, destination))
                .thenReturn(List.of(trip1, trip2));

        // Act
        List<Trip> trips = tripService.searchTrips(origin, destination);

        // Assert
        assertNotNull(trips);
        assertEquals(2, trips.size());
        assertTrue(trips.contains(trip1));
        assertTrue(trips.contains(trip2));
    }
}
