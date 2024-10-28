package com.crm.backend.service;

import com.crm.backend.model.Customer;
import com.crm.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    public Customer getCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }
}
