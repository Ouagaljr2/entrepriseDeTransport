package com.entreprise.transport.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.entreprise.transport.controller.TripController;
import com.entreprise.transport.model.Trip;
import com.entreprise.transport.service.TripService;


@WebMvcTest(TripController.class)
public class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @Test
    public void testGetAllTrips() throws Exception {
        when(tripService.findAllTrips()).thenReturn(List.of());

        mockMvc.perform(get("/trips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreateTrip() throws Exception {
        Trip trip = new Trip();
        trip.setOrigin("Paris");
        trip.setDestination("Lyon");
        when(tripService.saveTrip(any())).thenReturn(trip);

        mockMvc.perform(post("/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"origin\":\"Paris\", \"destination\":\"Lyon\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin").value("Paris"));
    }
}
