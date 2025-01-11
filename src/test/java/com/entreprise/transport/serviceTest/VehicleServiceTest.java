package com.entreprise.transport.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.repository.VehicleRepository;
import com.entreprise.transport.service.VehicleService;


@SpringBootTest
@RunWith(SpringRunner.class)
public class VehicleServiceTest {

    @Autowired
    private VehicleService vehicleService;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Test
    public void testFindAllVehicles() {
        List<Vehicle> vehicles = List.of(new Vehicle(), new Vehicle());
        when(vehicleRepository.findAll()).thenReturn(vehicles);
        assertEquals(2, vehicleService.findAllVehicles().size());
    }

    @Test
    public void testSaveVehicle() {
        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        assertNotNull(vehicleService.saveVehicle(vehicle));
    }

    @Test
    public void testDeleteVehicle() {
        int id = 1;
        doNothing().when(vehicleRepository).deleteById(id);
        vehicleService.deleteVehicle(id);
        verify(vehicleRepository, times(1)).deleteById(id);
    }
}
