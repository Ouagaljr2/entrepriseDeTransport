package com.entreprise.transport.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.repository.VehicleRepository;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.repository.TripRepository;
import com.entreprise.transport.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Utilisation de openMocks pour Mockito 3.x et au-delà
    }

    // Test pour récupérer tous les véhicules
    @Test
    void testFindAllVehicles() {
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle1, vehicle2));
        
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        
        assertNotNull(vehicles);
        assertEquals(2, vehicles.size());
    }

    // Test pour sauvegarder un véhicule
    @Test
    void testSaveVehicle() {
        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        
        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
        
        assertNotNull(savedVehicle);
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    // Test pour mettre à jour un véhicule
    @Test
    void testUpdateVehicle() {
        Vehicle existingVehicle = new Vehicle();
        existingVehicle.setRegistrationNumber("ABC123");
        existingVehicle.setStatus("Active");
        
        Vehicle updatedVehicle = new Vehicle();
        updatedVehicle.setRegistrationNumber("XYZ789");
        updatedVehicle.setStatus("Inactive");
        
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);
        
        Vehicle result = vehicleService.updateVehicle(1, updatedVehicle);
        
        assertEquals("XYZ789", result.getRegistrationNumber());
        assertEquals("Inactive", result.getStatus());
    }

    // Test pour vérifier que le véhicule n'est pas trouvé lors de la mise à jour
    @Test
    void testUpdateVehicleNotFound() {
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> vehicleService.updateVehicle(1, new Vehicle()));
    }

    // Test pour la suppression d'un véhicule
    @Test
    void testDeleteVehicle() {
        Vehicle vehicle = new Vehicle();
        Trip trip = new Trip();
        Driver driver = new Driver();
        driver.setStatus("Occupied");
        trip.setDriver(driver);
        
        when(tripRepository.findByVehicleId(anyInt())).thenReturn(Arrays.asList(trip));
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(vehicle));
        
        vehicleService.deleteVehicle(1);
        
        verify(driverRepository, times(1)).save(driver);
        verify(tripRepository, times(1)).deleteAll(anyList());
        verify(vehicleRepository, times(1)).deleteById(1);
        assertEquals("Disponible", driver.getStatus());
    }

    // Test pour rechercher des véhicules
    @Test
    void testSearchVehicles() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("XYZ789");
        
        when(vehicleRepository.findByRegistrationNumberContaining("XYZ")).thenReturn(Arrays.asList(vehicle));
        
        List<Vehicle> results = vehicleService.searchVehicles("XYZ");
        
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("XYZ789", results.get(0).getRegistrationNumber());
    }
}
