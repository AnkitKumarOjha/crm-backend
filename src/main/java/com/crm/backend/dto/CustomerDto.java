package com.crm.backend.dto;

import com.crm.backend.model.Customer;
import com.crm.backend.model.CustomerType;
import com.crm.backend.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CustomerDto {
    private String name;

    private String email;

    private String phoneNumber;

    private CustomerType customerType;



    public Customer addCustomer(){
        return Customer.builder()
                .name(this.name)
                .email(this.email)
                .customerType(this.customerType)
                .phoneNumber(this.phoneNumber)

                .build();
    }


}
