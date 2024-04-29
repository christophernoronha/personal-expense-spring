package com.cnor.expensetracker.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cnor.expensetracker.dtos.expenseresponse.ExpenseResponseDTO;
import com.cnor.expensetracker.dtos.request.expenserequest.ExpenseRequestDTO;
import com.cnor.expensetracker.entities.Expense;
import com.cnor.expensetracker.services.ExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/expenses/user/{userid}")
    @PreAuthorize("@preAuthorizeUser.isSameUser(#userid)")
    public ResponseEntity<?> addExpenseForUser(@Valid @RequestBody ExpenseRequestDTO requestDTO, @PathVariable("userid") String userId) {
        Expense expense = expenseService.addUserExpense(requestDTO, userId);
        
        var newExpenseURI = ServletUriComponentsBuilder
                                                .fromCurrentRequest()
                                                .path("/{id}")
                                                .buildAndExpand(expense)
                                                .toUri();

        return ResponseEntity.created(newExpenseURI).build();
    }

    @GetMapping("/expenses/{expenseid}/users/{userid}")
    @PreAuthorize("@preAuthorizeUser.isSameUser(#userid)")
    public ResponseEntity<ExpenseResponseDTO> getExpenseByExpenseIdForUser(@PathVariable("expenseid") String expenseId, @PathVariable("userid") String userId) {
        var expense = expenseService.getUserExpense(expenseId, userId);
        return ResponseEntity.ok(expense);
    }    
}
