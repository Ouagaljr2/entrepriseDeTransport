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

import com.entreprise.transport.controller.VehicleController;
import com.entreprise.transport.model.Vehicle;
import com.entreprise.transport.service.VehicleService;


@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Test
    public void testGetAllVehicles() throws Exception {
        when(vehicleService.findAllVehicles()).thenReturn(List.of());

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreateVehicle() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel("Tesla");
        when(vehicleService.saveVehicle(any())).thenReturn(vehicle);

        mockMvc.perform(post("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"model\":\"Tesla\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Tesla"));
    }
}
