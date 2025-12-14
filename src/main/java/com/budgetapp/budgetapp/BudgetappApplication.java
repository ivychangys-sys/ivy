package com.budgetapp.budgetapp;

import com.budgetapp.budgetapp.entity.User;
import com.budgetapp.budgetapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BudgetappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetappApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            String testToken = "46801b2e-bbf8-409e-bf20-6319ccfb3310";
            if (userRepository.findByAccessToken(testToken).isEmpty()) {
                User user = new User();
                user.setUsername("ivy");
                user.setPassword("1234");
                user.setAccessToken(testToken);
                userRepository.save(user);
                System.out.println("測試使用者已建立: " + user);
            } else {
                System.out.println("測試使用者已存在");
            }
        };
    }
}
