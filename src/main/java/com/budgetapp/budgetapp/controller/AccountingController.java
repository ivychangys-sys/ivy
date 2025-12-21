package com.budgetapp.budgetapp.controller;

import com.budgetapp.budgetapp.dto.AccountingDTO;
import com.budgetapp.budgetapp.entity.Accounting;
import com.budgetapp.budgetapp.entity.User;
import com.budgetapp.budgetapp.repository.AccountingRepository;
import com.budgetapp.budgetapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounting")
public class AccountingController {

    @Autowired
    private AccountingRepository accountingRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser(String accessToken) {
        return userRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<AccountingDTO> getAll(@RequestHeader("Authorization") String token) {
        User user = getCurrentUser(token);
        return accountingRepository.findByUser(user).stream()
                .map(AccountingDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/total")
    public Map<String, Double> getTotal(@RequestHeader("Authorization") String token) {
        User user = getCurrentUser(token);
        double total = accountingRepository.findByUser(user).stream()
                .mapToDouble(a -> Math.abs(a.getAmount()))
                .sum();
        return Collections.singletonMap("total", total);
    }

    @GetMapping("/category")
    public Map<String, Double> getCategory(@RequestHeader("Authorization") String token) {
        User user = getCurrentUser(token);
        Map<String, Double> result = new HashMap<>();

        accountingRepository.findByUser(user).forEach(a -> {
            String cat = a.getCategory() != null ? a.getCategory() : "Uncategorized";
            result.put(cat, result.getOrDefault(cat, 0.0) + Math.abs(a.getAmount()));
        });

        return result;
    }

    @PostMapping
    public AccountingDTO create(
            @RequestHeader("Authorization") String token,
            @RequestBody Accounting accounting
    ) {
        User user = getCurrentUser(token);
        accounting.setUser(user);
        Accounting saved = accountingRepository.save(accounting);
        return new AccountingDTO(saved);
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Collections.singletonMap("status", "alive");
    }
}
