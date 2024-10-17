package com.crm.backend.model;
import javax.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // e.g., "ADMIN", "SALES_REP"

    // Getters and Setters
}

