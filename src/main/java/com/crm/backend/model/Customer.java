package com.crm.backend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "customer")
    private Set<Contact> contacts;

    // Getters and Setters
}

