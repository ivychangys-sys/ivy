package com.budgetapp.budgetapp.dto;

import com.budgetapp.budgetapp.entity.Accounting;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountingDTO {

    private Long key; 
    private String label;
    private String data;
    private String date;       
    private double amount;
    private String category;

    public AccountingDTO(Accounting a) {
        this.key = a.getId();
        this.label = a.getTitle();
        this.data = "";
        this.amount = a.getAmount();
        this.category = a.getCategory() != null ? a.getCategory() : "Uncategorized";

        this.date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public Long getKey() { return key; }
    public String getLabel() { return label; }
    public String getData() { return data; }
    public String getDate() { return date; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
}
