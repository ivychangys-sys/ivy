package com.budgetapp.budgetapp.repository;

import com.budgetapp.budgetapp.entity.Accounting;
import com.budgetapp.budgetapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountingRepository extends JpaRepository<Accounting, Long> {
    List<Accounting> findByUser(User user);
}
