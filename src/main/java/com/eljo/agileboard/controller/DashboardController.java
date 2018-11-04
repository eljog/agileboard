package com.eljo.agileboard.controller;

import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by Eljo.George on 11/2/2018.
 */

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/bar/story-count-by-status-and-owner")
    public ResponseEntity<Map<String, Object>> barChartOfStoryCountByStatusAndOwner() throws InvalidUserException {
        return ResponseEntity.ok(dashboardService.barChartOfStoryCountByStatusAndOwner());
    }

    @GetMapping("/pie/story-count-by-owner")
    public ResponseEntity<List<Map<String, Object>>> pieChartOfStoryCountByOwner() throws InvalidUserException {
        return ResponseEntity.ok(dashboardService.pieChartOfStoryCountByOwner());
    }

    @GetMapping("/table/story-count-by-status-and-owner")
    public ResponseEntity<List<Map<String, Object>>> tableOfStoryCountByOwnerAndStatus() throws InvalidUserException {
        return ResponseEntity.ok(dashboardService.tableOfStoryCountByOwnerAndStatus());
    }
}
