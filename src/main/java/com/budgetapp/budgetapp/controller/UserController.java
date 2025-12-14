package com.budgetapp.budgetapp.controller;

import com.budgetapp.budgetapp.entity.User;
import com.budgetapp.budgetapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users") 
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters.");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User created successfully.");
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Invalid password.");
        }

        String token = UUID.randomUUID().toString();
        user.setAccessToken(token);
        userRepository.save(user);

        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/token")
    public ResponseEntity<?> deleteToken(@RequestParam String token) {
        Optional<User> optionalUser = userRepository.findByAccessToken(token);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("Token not found.");
        }

        User user = optionalUser.get();
        user.setAccessToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Token deleted successfully.");
    }
}
