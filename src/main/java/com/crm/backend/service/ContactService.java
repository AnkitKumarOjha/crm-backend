package com.crm.backend.service;

import com.crm.backend.model.Contact;
import com.crm.backend.model.Customer;
import com.crm.backend.repository.ContactRepository;
import com.crm.backend.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Contact addContact(Long customerId, Contact contactDetails) {
        // Find the customer by ID
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Customer customer = optionalCustomer.get();

        contactDetails.setCustomer(customer);
        return contactRepository.save(contactDetails);
    }

    public Optional<Contact> getContactById(Long contactId) {
        return contactRepository.findById(contactId);
    }

    // Update an existing contact
    public Contact updateContact(Long contactId, Contact contactDetails) {
        Contact existingContact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + contactId));

        existingContact.setTitle(contactDetails.getTitle());
        existingContact.setNotes(contactDetails.getNotes());
        existingContact.setDate(contactDetails.getDate());

        return contactRepository.save(existingContact);
    }

    // Delete a contact
    public void deleteContact(Long contactId) {
        Contact existingContact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + contactId));

        contactRepository.delete(existingContact);
    }

    // Get all contacts for a specific customer
    public List<Contact> getAllContactsByCustomerId(Long customerId) {
        return contactRepository.findAllByCustomerId(customerId);
    }
}
