package com.crm.backend.service;

import com.crm.backend.dto.CustomerDto;
import com.crm.backend.model.Customer;
import com.crm.backend.model.User;
import com.crm.backend.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;
    public Optional<Customer> getCustomerById(Long id){
        return customerRepository.findById(id);
    }

    public Customer addCustomerBySalesRepresentative(CustomerDto customerDto,String userName) {
        Customer customer=customerDto.addCustomer();
        User salesRep= userService.getUserByName(userName);
        customer.setCreatedBy(salesRep);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(long customerId, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));

        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setCustomerType(customerDto.getCustomerType());

        return customerRepository.save(customer);
    }

    public void deleteCustomerById(long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));

        customerRepository.delete(customer);
    }
}
