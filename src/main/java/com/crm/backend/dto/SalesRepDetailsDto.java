package com.crm.backend.dto;

import com.crm.backend.model.Customer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesRepDetailsDto {
    private long id;
    private String name;
    private String email;
    private long sales;
    private long customersManaged;
    private List<CustomerListDto> customers;

    public SalesRepDetailsDto(long id, String name, String email, long sales, long customersManaged) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.sales = sales;
        this.customersManaged = customersManaged;
        this.customers = null; // Set to null or you can leave it uninitialized
    }

}
