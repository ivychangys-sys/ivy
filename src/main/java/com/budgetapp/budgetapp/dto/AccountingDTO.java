package com.budgetapp.budgetapp.dto;

import com.budgetapp.budgetapp.entity.Accounting;

public class AccountingDTO {
    private Long id;
    private String title;
    private double amount;
    private String category;

    public AccountingDTO(Accounting a) {
        this.id = a.getId();
        this.title = a.getTitle();
        this.amount = a.getAmount();
        this.category = a.getCategory() != null ? a.getCategory() : "Uncategorized";
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
}
