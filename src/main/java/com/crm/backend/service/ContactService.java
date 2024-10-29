package com.crm.backend.service;

import com.crm.backend.dto.CreateContactRequestDto;
import com.crm.backend.model.Contact;
import com.crm.backend.model.Customer;
import com.crm.backend.repository.ContactRepository;
import com.crm.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public Contact createContact(Long customerId, CreateContactRequestDto contactRequestDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        Contact contact = new Contact();
        contact.setTitle(contactRequestDto.getTitle());
        contact.setNotes(contactRequestDto.getNotes());
        contact.setDate(contactRequestDto.getDate());
        contact.setCustomer(customer);

        return contactRepository.save(contact);
    }

    public void deleteContact(Long customerId, Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        // You may want to check if the contact belongs to the specified customer
        if (!contact.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("Contact does not belong to this customer");
        }

        contactRepository.delete(contact);
    }
}
