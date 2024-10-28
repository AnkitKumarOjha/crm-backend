package com.crm.backend.service;

import com.crm.backend.model.User;
import com.crm.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUserById(long salesRepId) {
        return userRepository.findById(salesRepId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + salesRepId));
    }

    public User getUserByName(String userName) {
        return userRepository.findByEmail(userName);
    }
}
