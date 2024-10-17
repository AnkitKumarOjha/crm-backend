//package com.crm.backend.controller;
//
//import com.crm.backend.model.User;
//import com.crm.backend.service.UserService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthenticationController {
//    private final UserService userService;
//
//    public AuthenticationController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//        User user = userService.findByEmail(loginRequest.getEmail());
//        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            // Generate JWT token here
//            return "Login successful";
//        } else {
//            return "Invalid credentials";
//        }
//    }
//}
//
