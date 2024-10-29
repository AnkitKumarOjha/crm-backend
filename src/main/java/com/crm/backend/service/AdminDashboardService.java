package com.crm.backend.service;

import com.crm.backend.dto.*;
import com.crm.backend.model.Customer;
import com.crm.backend.model.Role;
import com.crm.backend.model.User;
import com.crm.backend.repository.CustomerRepository;
import com.crm.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminDashboardService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<SalesRepPerformanceDto> getTopSalesRepsBySales() {
        List<Object[]> results = customerRepository.findTopSalesRepsBySales();
        return results.stream()
                .limit(5) // Limiting to the top 5 after fetching
                .map(result -> new SalesRepPerformanceDto((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    public List<SalesRepPerformanceDto> getTopSalesRepsByConversionRate() {
        List<Object[]> results = customerRepository.findTopSalesRepsByConversionRate();
        return results.stream()
                .limit(5) // Limiting to the top 5 after fetching
                .map(result -> new SalesRepPerformanceDto((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    public long getTotalSales() {
        return customerRepository.getTotalSales();
    }

//    public List<User> getTotalSalesReps() {
//        return userRepository.findByRole(Role.SALES_REP);
//    }

    public Page<User> getTotalSalesReps(Pageable pageable) {
        return userRepository.findByRole(Role.SALES_REP, pageable);
    }

//    public List<SalesRepListDto> getSalesRepList() {
//        return userRepository.findSalesRepList();
//    }


    public Page<SalesRepListDto> getSalesRepList(Pageable pageable) {
        return userRepository.findSalesRepList(pageable);
    }

    public SalesRepDetailsDto getSalesRepDetails(Long id) {

        SalesRepDetailsDto salesRepDetails = userRepository.findSalesRepDetailsById(id);

        List<CustomerListDto> customers = customerRepository.findCustomersBySalesRepsId(id);

        salesRepDetails.setCustomers(customers);

        return salesRepDetails;
    }

    public User createUser(@Valid User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    @Transactional
    public User updateUser(Long userId, @Valid UserUpdateRequestDto request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Update the user's details
        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        existingUser.setRole(request.getRole());

        // Save the updated user back to the database
        return userRepository.save(existingUser);

    }
    @Transactional
    public void deleteUser(Long userId) {
        User user=userRepository.findById(userId).get();
        customerRepository.deleteByCreatedBy(user);
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public SalesRepDetailsDto getSalesRepDetailsByEmail(@Valid String email) {
        SalesRepDetailsDto salesRepDetails = userRepository.findSalesRepDetailsByEmail(email);

        List<CustomerListDto> customers = customerRepository.findCustomersBySalesRepsId(salesRepDetails.getId());

        salesRepDetails.setCustomers(customers);

        return salesRepDetails;
    }
}