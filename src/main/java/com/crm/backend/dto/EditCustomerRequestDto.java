package com.crm.backend.dto;

import com.crm.backend.model.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCustomerRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
    private CustomerType customerType;

}
