package com.cnor.expensetracker.dtos.expenseresponse;

import java.math.BigDecimal;

import com.cnor.expensetracker.dtos.request.expenserequest.ExpenseType;

public record ExpenseResponseDTO(String id, String description, ExpenseType expenseType, BigDecimal amount, String userId) {
    
}
