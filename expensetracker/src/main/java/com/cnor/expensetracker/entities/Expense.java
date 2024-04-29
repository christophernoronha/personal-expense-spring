package com.cnor.expensetracker.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cnor.expensetracker.dtos.request.expenserequest.ExpenseType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Expense {
    
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDate expenseDate;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private ExpenseType expenseType;

    private BigDecimal amount;

    @ManyToOne
    private User user;

}
