package com.crm.backend.controller;

import com.crm.backend.dto.SalesRepDetailsDto;
import com.crm.backend.dto.SalesRepListDto;
import com.crm.backend.dto.SalesRepPerformanceDto;
import com.crm.backend.dto.UserUpdateRequestDto;
import com.crm.backend.model.User;
import com.crm.backend.service.AdminDashboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

//    @GetMapping("/total-sales-reps")
//    public List<User> getTotalSalesReps() {
//        return adminDashboardService.getTotalSalesReps();
//    }

    @GetMapping("/total-sales-reps")
    public Page<User> getTotalSalesReps(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return adminDashboardService.getTotalSalesReps(pageable);
    }

//    @GetMapping("/sales-rep-list")
//    public ResponseEntity<List<SalesRepListDto>> getSalesRepList() {
//        List<SalesRepListDto> salesRepList = adminDashboardService.getSalesRepList();
//        return new ResponseEntity<>(salesRepList, HttpStatus.OK);
//    }

    @GetMapping("/sales-rep-list")
    public ResponseEntity<Page<SalesRepListDto>> getSalesRepList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SalesRepListDto> salesRepList = adminDashboardService.getSalesRepList(pageable);
        return ResponseEntity.ok(salesRepList);
    }


    @GetMapping("/sales-reps/{id}")
    public SalesRepDetailsDto getSalesRepDetails(@PathVariable Long id) {
        return adminDashboardService.getSalesRepDetails(id);
    }

    @GetMapping("/sales")
    public SalesRepDetailsDto getSalesRepDetailsByEmail(@RequestParam String email) {
        return adminDashboardService.getSalesRepDetailsByEmail(email);
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
    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequestDto request) {
        User updatedUser = adminDashboardService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        adminDashboardService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }


}

