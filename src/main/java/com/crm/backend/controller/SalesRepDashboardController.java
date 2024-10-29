package com.crm.backend.controller;

import com.crm.backend.dto.CustomerDto;
import com.crm.backend.dto.CustomerListDto;
import com.crm.backend.model.Customer;
import com.crm.backend.model.User;
import com.crm.backend.service.CustomerService;
import com.crm.backend.service.SalesDashboardService;
import com.crm.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("hasRole('SALES_REP')")
@RestController
//@RequestMapping("/api/salesrep") // Optional: Consider adding a base path for organization
public class SalesRepDashboardController {

    private final SalesDashboardService salesDashboardService;
    private final CustomerService customerService;
    private final UserService userService;
    private CustomerDto customerDto;
    @Autowired
    public SalesRepDashboardController(SalesDashboardService salesDashboardService, CustomerService customerService, UserService userService) {
        this.salesDashboardService = salesDashboardService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("/total-noOfCustomer")
    public long getTotalNoOfCustomer() {
        User user=userService.getUserByName(getCurrentUserName());
        long saleRepId = user.getId();
        return salesDashboardService.getTotalNoCustomer(saleRepId);
    }
    //
    @GetMapping("/total-noOfLead")
    public long getTotalNoOfLead() {
        User user=userService.getUserByName(getCurrentUserName());
        long saleRepId = user.getId();
        return salesDashboardService.getTotalNoLead(saleRepId);
    }
    //
    @GetMapping("/conversion-Ratio")
    public ResponseEntity<Double> conversionRate() {
        long totalCustomers = getTotalNoOfCustomer();
        long totalLeads = getTotalNoOfLead();
        double conversionRate = totalCustomers + totalLeads > 0
                ? (double) (totalCustomers - totalLeads) / (totalCustomers)
                : 0.0; // Prevent division by zero
        return ResponseEntity.ok(conversionRate);
    }

    @GetMapping("/salesforSalesRep")
    public ResponseEntity<Long> sales(){
        long totalCustomers = getTotalNoOfCustomer();
        long totalLeads = getTotalNoOfLead();
        if(totalCustomers-totalLeads>=0){
            return ResponseEntity.ok(totalCustomers-totalLeads);
        }else{
            return ResponseEntity.ok(0L);
        }
    }
    //
    @GetMapping("/customer-list")
    public ResponseEntity<List<CustomerListDto>> getCustomerList() {
        User user=userService.getUserByName(getCurrentUserName());
        long saleRepId = user.getId();
        List<CustomerListDto> salesRepList = salesDashboardService.getCustomerList(saleRepId);
        return new ResponseEntity<>(salesRepList, HttpStatus.OK);
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<Customer> createCustomerBySalesRepresentative(@Valid @RequestBody CustomerDto customerDto) {
//        long salesRepId =userService.getgetCurrentUserName() ;
        String userName=getCurrentUserName();
        Customer createdCustomer = customerService.addCustomerBySalesRepresentative(customerDto,getCurrentUserName());
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }


    @PutMapping("/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long customerId, @Valid @RequestBody CustomerDto customerDto) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer deleted successfully.");
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            throw new IllegalStateException("User is not authenticated");
        }
//        long id=customerDto.getId();
//        return id;
    }



}
