package com.crm.backend.controller;

import com.crm.backend.dto.*;
import com.crm.backend.model.Customer;
import com.crm.backend.model.User;
import com.crm.backend.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/{id}")
    public Customer getSalesRepDetails(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/create-customer")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateCustomerRequestDto createCustomerRequestDto) {
        try {
            Customer customer = customerService.createCustomer(createCustomerRequestDto);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-customer/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody EditCustomerRequestDto request) {
         customerService.updateCustomer(id, request);
        return ResponseEntity.ok("Customer Updated Successfully !!");
    }
    @DeleteMapping("/delete-customer/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        customerService.deleteCustomer(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
