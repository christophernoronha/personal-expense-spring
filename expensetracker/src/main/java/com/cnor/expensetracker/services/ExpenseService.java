package com.cnor.expensetracker.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cnor.expensetracker.dtos.expenseresponse.ExpenseResponseDTO;
import com.cnor.expensetracker.dtos.request.expenserequest.ExpenseRequestDTO;
import com.cnor.expensetracker.entities.Expense;
import com.cnor.expensetracker.entities.User;
import com.cnor.expensetracker.repositories.ExpenseRepository;
import com.cnor.expensetracker.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    

    private final ExpenseRepository expenseRepository;

    private final UserRepository userRepository;

    public Expense addUserExpense(ExpenseRequestDTO requestDTO, String userId){
        Expense expense = mapDTOtoModel(requestDTO, userId);
        return expenseRepository.save(expense);
    }

    public ExpenseResponseDTO getUserExpense(String expenseId, String userId){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException(userId + " not found"));

        Expense expense = expenseRepository.findExpenseByExpenseIdAndUser(expenseId, user)
                            .orElseThrow(() -> new UsernameNotFoundException(userId + " not found"));

        return mapModelToDTO(expense, userId);
    }

    

    private Expense mapDTOtoModel(ExpenseRequestDTO dto, String userId){
        Expense expense = new Expense();
        expense.setDescription(dto.description());
        expense.setExpenseDate(dto.date());
        expense.setExpenseType(dto.expenseType());
        expense.setAmount(dto.amount());

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException(userId + " not found"));

        expense.setUser(user);

        return expense;
    }

    private ExpenseResponseDTO mapModelToDTO(Expense expense, String userId){
        return new ExpenseResponseDTO(expense.getId(), expense.getDescription(), expense.getExpenseType(), expense.getAmount(), userId);
    }

}
