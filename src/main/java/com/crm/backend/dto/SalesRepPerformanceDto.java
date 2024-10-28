package com.crm.backend.dto;

import com.crm.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesRepPerformanceDto {
    private String salesRepName;
    private Long performanceMetric;
}

