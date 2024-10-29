package com.crm.backend.repository;

import com.crm.backend.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    //Contact findById(Long id);
    Optional<Contact> findById(Long id);
    List<Contact> findAllByCustomerId(Long customerId);
}