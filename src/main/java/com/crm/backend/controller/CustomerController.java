package com.crm.backend.controller;

import com.crm.backend.model.Contact;
import com.crm.backend.model.Customer;
import com.crm.backend.service.ContactService;
import com.crm.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ContactService contactService;

    @GetMapping("/customers/{id}")
    public Optional<Customer> getSalesRepDetails(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/{id}/createContact")
    public ResponseEntity<Contact> createContact(@PathVariable Long id, @RequestBody Contact contact){
//        Customer createdCustomer = customerService.addCustomerBySalesRepresentative(customerDto,getCurrentUserName());
//        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);

        Contact createdContact=contactService.addContact(id,contact);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
   }

    // Read a specific contact
    @GetMapping("/{contactId}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long contactId) {
        Optional<Contact> contactOptional = contactService.getContactById(contactId);
        Contact contact=contactOptional.get();
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    // Update an existing contact
    @PutMapping("/updatecontact/{contactId}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long contactId, @RequestBody Contact contactDetails) {
        Contact updatedContact = contactService.updateContact(contactId, contactDetails);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    // Delete a contact
    @DeleteMapping("deletecontact/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long contactId) {
        contactService.deleteContact(contactId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all contacts for a specific customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Contact>> getAllContactsByCustomerId(@PathVariable Long customerId) {
        List<Contact> contacts = contactService.getAllContactsByCustomerId(customerId);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }


}
