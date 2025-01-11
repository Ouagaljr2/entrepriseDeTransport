package com.entreprise.transport.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.transport.service.DashboardService;


@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public Map<String, Long> getStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalDrivers", dashboardService.getTotalDrivers());
        stats.put("totalVehicles", dashboardService.getTotalVehicles());
        stats.put("totalTrips", dashboardService.getTotalTrips());
        return stats;
    }
}