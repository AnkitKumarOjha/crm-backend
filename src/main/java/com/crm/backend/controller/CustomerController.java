package com.crm.backend.controller;

import com.crm.backend.model.Customer;
import com.crm.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/{id}")
    public Optional<Customer> getSalesRepDetails(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}
