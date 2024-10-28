package com.crm.backend.dto;

import com.crm.backend.model.CustomerType;
import com.crm.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private CustomerType customerType;
    private User createdBy;
}
