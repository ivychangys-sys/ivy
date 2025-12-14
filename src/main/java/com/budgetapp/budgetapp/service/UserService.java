package com.budgetapp.budgetapp.service;

import com.budgetapp.budgetapp.entity.User;
import com.budgetapp.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService { 

    private final UserRepository userRepository;
    private final Map<String, Long> tokenStore = new HashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password) {
        User user = new User(username, password);
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return null;

        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) return null;

        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getId());
        return token;
    }

    public boolean logout(String token) {
        return tokenStore.remove(token) != null;
    }

    public boolean isValidToken(String token) {
        return tokenStore.containsKey(token);
    }

    public Long getUserIdByToken(String token) {
        return tokenStore.get(token);
    }
}
