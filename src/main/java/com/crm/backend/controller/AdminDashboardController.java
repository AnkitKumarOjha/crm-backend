package com.crm.backend.controller;

import com.crm.backend.dto.SalesRepDetailsDto;
import com.crm.backend.dto.SalesRepListDto;
import com.crm.backend.dto.SalesRepPerformanceDto;
import com.crm.backend.model.User;
import com.crm.backend.service.AdminDashboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;


    @GetMapping("/top-sales")
    public List<SalesRepPerformanceDto> getTopSalesRepsBySales() {
        return adminDashboardService.getTopSalesRepsBySales();
    }

    @GetMapping("/top-conversion")
    public List<SalesRepPerformanceDto> getTopSalesRepsByConversionRate() {
        return adminDashboardService.getTopSalesRepsByConversionRate();
    }

    @GetMapping("/total-sales")
    public long getTotalSales() {
        return adminDashboardService.getTotalSales();
    }

    @GetMapping("/total-sales-reps")
    public List<User> getTotalSalesReps() {
        return adminDashboardService.getTotalSalesReps();
    }

    @GetMapping("/sales-rep-list")
    public ResponseEntity<List<SalesRepListDto>> getSalesRepList() {
        List<SalesRepListDto> salesRepList = adminDashboardService.getSalesRepList();
        return new ResponseEntity<>(salesRepList, HttpStatus.OK);
    }
    @GetMapping("/sales-reps/{id}")
    public SalesRepDetailsDto getSalesRepDetails(@PathVariable Long id) {
        return adminDashboardService.getSalesRepDetails(id);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User newUser = adminDashboardService.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



}

