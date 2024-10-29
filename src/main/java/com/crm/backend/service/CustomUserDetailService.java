package com.crm.backend.service;

import com.crm.backend.model.User;
import com.crm.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        System.out.println("User found: " + user.getEmail());
        System.out.println("Stored password (hashed): " + user.getPassword());
        System.out.println("Role: " + user.getRole());

//                PasswordEncoder encoder = new BCryptPasswordEncoder();
//                user.setPassword(encoder.encode("password"));
//                userRepository.save(user);


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user) // Call the method to get authorities
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        // Since role is now a single enum, we need to convert it to GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
}
