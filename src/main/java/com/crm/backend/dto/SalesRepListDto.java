package com.crm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesRepListDto {
    private long id;
    private String name;
    private long sales;
    private long customersManaged;
}
