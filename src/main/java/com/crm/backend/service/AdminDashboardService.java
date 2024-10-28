package com.crm.backend.service;

import com.crm.backend.dto.CustomerListDto;
import com.crm.backend.dto.SalesRepDetailsDto;
import com.crm.backend.dto.SalesRepListDto;
import com.crm.backend.dto.SalesRepPerformanceDto;
import com.crm.backend.model.Customer;
import com.crm.backend.model.Role;
import com.crm.backend.model.User;
import com.crm.backend.repository.CustomerRepository;
import com.crm.backend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<User> getTotalSalesReps() {
        return userRepository.findByRole(Role.SALES_REP);
    }

    public List<SalesRepListDto> getSalesRepList() {
        return userRepository.findSalesRepList();
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
}