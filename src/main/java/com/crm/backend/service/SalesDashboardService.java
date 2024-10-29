package com.crm.backend.service;

import com.crm.backend.dto.CustomerListDto;
import com.crm.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesDashboardService {


    @Autowired
    private CustomerRepository customerRepository;

    public long getTotalNoCustomer(long saleRepId) {
        return customerRepository.getTotalNoCustomer(saleRepId);
    }


    public long getTotalNoLead(long saleRepId) {
        return customerRepository.getTotalNoLead(saleRepId);
    }

    public List<CustomerListDto> getCustomerList(long saleRepId) {
        List<CustomerListDto> customers = customerRepository.findCustomersBySalesRepsId(saleRepId);
        return  customers;
    }
}
