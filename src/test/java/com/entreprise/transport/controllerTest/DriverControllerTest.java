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

import com.entreprise.transport.controller.DriverController;
import com.entreprise.transport.model.Driver;
import com.entreprise.transport.service.DriverService;


@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @Test
    public void testGetAllDrivers() throws Exception {
        when(driverService.findAllDrivers()).thenReturn(List.of(new Driver(), new Driver()));

        mockMvc.perform(get("/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreateDriver() throws Exception {
        Driver driver = new Driver();
        driver.setName("John Doe");
        when(driverService.saveDriver(any())).thenReturn(driver);

        mockMvc.perform(post("/drivers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }
}
