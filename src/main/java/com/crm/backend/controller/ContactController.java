package com.crm.backend.controller;

import com.crm.backend.dto.CreateContactRequestDto;
import com.crm.backend.model.Contact;
import com.crm.backend.repository.ContactRepository;
import com.crm.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/create-contact/{customerId}")
    public ResponseEntity<Contact> createContact(
            @PathVariable Long customerId,
            @RequestBody CreateContactRequestDto contactRequestDto) {

        Contact createdContact = contactService.createContact(customerId, contactRequestDto);
        return ResponseEntity.ok(createdContact);
    }
    @DeleteMapping("/{customerId}/delete-contact/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable Long customerId, @PathVariable Long contactId) {
        try {
            contactService.deleteContact(customerId, contactId);
            return ResponseEntity.ok("Contact deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting contact: " + e.getMessage());
        }
    }
}
