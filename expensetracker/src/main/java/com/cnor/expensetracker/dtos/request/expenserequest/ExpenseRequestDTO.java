package com.cnor.expensetracker.dtos.request.expenserequest;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ExpenseRequestDTO(@NotNull LocalDate date, @NotNull @Size(min = 2, max = 30, message = "{description.invalid.message}")String description, @NotNull @Pattern(regexp = "NEEDS|WANTS|SAVINGS", message = "{expenseType.invalid.message}") ExpenseType expenseType, @NotNull BigDecimal amount) {
    
}
