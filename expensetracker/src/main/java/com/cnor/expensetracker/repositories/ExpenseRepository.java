package com.cnor.expensetracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cnor.expensetracker.entities.Expense;
import com.cnor.expensetracker.entities.User;

public interface ExpenseRepository extends JpaRepository<Expense, String> {

    @Query("""
            SELECT e from Expense e where e.id = :expenseId and e.user = :user
            """)
    public Optional<Expense> findExpenseByExpenseIdAndUser(String expenseId, User user);
    
} 