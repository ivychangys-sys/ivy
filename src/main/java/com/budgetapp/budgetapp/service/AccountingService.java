package com.budgetapp.budgetapp.service;

import com.budgetapp.budgetapp.entity.Accounting;
import com.budgetapp.budgetapp.entity.User;
import com.budgetapp.budgetapp.repository.AccountingRepository;
import com.budgetapp.budgetapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountingService {

    @Autowired
    private AccountingRepository accountingRepository;

    @Autowired
    private UserRepository userRepository;

    public void createAccounting(Accounting accounting, String token) {
        User user = userRepository.findByAccessToken(token)
                .orElseThrow(() -> new RuntimeException("User not found for token: " + token));
        accounting.setUser(user);
        accountingRepository.save(accounting);
    }

    public Double getTotal(String token) {
        User user = userRepository.findByAccessToken(token)
                .orElse(null);
        if (user == null) return 0.0;

        return accountingRepository.findByUser(user)
                .stream()
                .mapToDouble(a -> Math.max(a.getAmount(), 0))
                .sum();
    }

    public Map<String, Double> getCategoryTotal(String token) {
        User user = userRepository.findByAccessToken(token)
                .orElse(null);
        Map<String, Double> result = new HashMap<>();
        if (user == null) return result;

        List<Accounting> records = accountingRepository.findByUser(user);
        for (Accounting a : records) {
            if (a.getAmount() <= 0) continue;
            result.put(a.getCategory(),
                    result.getOrDefault(a.getCategory(), 0.0) + a.getAmount());
        }
        return result;
    }
}
