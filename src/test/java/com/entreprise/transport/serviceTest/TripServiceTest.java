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

import com.entreprise.transport.model.Trip;
import com.entreprise.transport.repository.TripRepository;
import com.entreprise.transport.service.TripService;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TripServiceTest {

    @Autowired
    private TripService tripService;

    @MockBean
    private TripRepository tripRepository;

    @Test
    public void testFindAllTrips() {
        List<Trip> trips = List.of(new Trip(), new Trip());
        when(tripRepository.findAll()).thenReturn(trips);
        assertEquals(2, tripService.findAllTrips().size());
    }

    @Test
    public void testSaveTrip() {
        Trip trip = new Trip();
        when(tripRepository.save(trip)).thenReturn(trip);
        assertNotNull(tripService.saveTrip(0, 0, trip));
    }

    @Test
    public void testDeleteTrip() {
        int id = 1;
        doNothing().when(tripRepository).deleteById(id);
        tripService.deleteTrip(id);
        verify(tripRepository, times(1)).deleteById(id);
    }
}
