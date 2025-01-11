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

import com.entreprise.transport.model.Driver;
import com.entreprise.transport.repository.DriverRepository;
import com.entreprise.transport.service.DriverService;


@SpringBootTest
@RunWith(SpringRunner.class)
public class DriverServiceTest {

    @Autowired
    private DriverService driverService;

    @MockBean
    private DriverRepository driverRepository;

    @Test
    public void testFindAllDrivers() {
        List<Driver> drivers = List.of(new Driver(), new Driver());
        when(driverRepository.findAll()).thenReturn(drivers);
        assertEquals(2, driverService.findAllDrivers().size());
    }

    @Test
    public void testSaveDriver() {
        Driver driver = new Driver();
        when(driverRepository.save(driver)).thenReturn(driver);
        assertNotNull(driverService.saveDriver(driver));
    }

    @Test
    public void testDeleteDriver() {
        int id = 1;
        doNothing().when(driverRepository).deleteById(id);
        driverService.deleteDriver(id);
        verify(driverRepository, times(1)).deleteById(id);
        
        assertEquals(1, 3);
    }
}
