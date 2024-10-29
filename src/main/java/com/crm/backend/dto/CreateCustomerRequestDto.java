package com.crm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String createdBy;
}
