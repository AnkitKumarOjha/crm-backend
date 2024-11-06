package com.crm.backend.service;

import com.crm.backend.dto.CreateCustomerRequestDto;
import com.crm.backend.dto.EditCustomerRequestDto;
import com.crm.backend.model.Customer;
import com.crm.backend.model.CustomerType;
import com.crm.backend.model.User;
import com.crm.backend.repository.CustomerRepository;
import com.crm.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    public Customer createCustomer(CreateCustomerRequestDto createCustomerRequestDto) {
        if (customerRepository.existsByEmail(createCustomerRequestDto.getEmail())) {
            throw new IllegalArgumentException("Customer with email already exists !");
        }
        // Fetch the user who is creating the customer using their email
        User createdByUser = userRepository.findByEmail(createCustomerRequestDto.getCreatedBy())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create a new Customer object
        Customer newCustomer = new Customer();
        newCustomer.setName(createCustomerRequestDto.getName());
        newCustomer.setEmail(createCustomerRequestDto.getEmail());
        newCustomer.setPhoneNumber(createCustomerRequestDto.getPhoneNumber());
        newCustomer.setCustomerType(CustomerType.LEAD); // Assuming new customers start as leads
        newCustomer.setCreatedBy(createdByUser);

        // Save the customer
        customerRepository.save(newCustomer);

        // Return the DTO (or modify this to return other responses as needed)
        return newCustomer;
    }
    @Transactional
    public void deleteCustomer(Long userId) {
        Customer customer=customerRepository.findCustomerById(userId);
        customerRepository.delete(customer);
    }
    @Transactional
    public void updateCustomer(Long id, @Valid EditCustomerRequestDto request) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Update the user's details
        existingCustomer.setName(request.getName());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setPhoneNumber(request.getPhoneNumber());
        existingCustomer.setCustomerType(request.getCustomerType());


        // Save the updated user back to the database
        customerRepository.save(existingCustomer);
    }
}
