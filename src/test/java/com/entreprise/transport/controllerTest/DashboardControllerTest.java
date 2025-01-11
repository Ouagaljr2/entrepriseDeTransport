package com.entreprise.transport.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.entreprise.transport.controller.DashboardController;
import com.entreprise.transport.service.DashboardService;



@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @Test
    public void testGetStatistics() throws Exception {
        Map<String, Long> stats = Map.of("totalDrivers", 10L, "totalVehicles", 5L, "totalTrips", 20L);
        when(dashboardService.getTotalDrivers()).thenReturn(10L);
        when(dashboardService.getTotalVehicles()).thenReturn(5L);
        when(dashboardService.getTotalTrips()).thenReturn(20L);

        mockMvc.perform(get("/dashboard/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalDrivers").value(10))
                .andExpect(jsonPath("$.totalVehicles").value(5))
                .andExpect(jsonPath("$.totalTrips").value(20));
    }
}
