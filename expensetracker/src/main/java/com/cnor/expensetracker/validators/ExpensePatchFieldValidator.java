package com.cnor.expensetracker.validators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.cglib.core.Local;

import com.cnor.expensetracker.dtos.request.expenserequest.ExpensePatchField;
import com.cnor.expensetracker.dtos.request.expenserequest.ExpensePatchRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpensePatchFieldValidator implements ConstraintValidator<ExpensePatchFieldValidation, ExpensePatchRequestDTO>{

    @Override
    public boolean isValid(ExpensePatchRequestDTO dto, ConstraintValidatorContext context) {
        ExpensePatchField field = dto.patchRequestField();
        String value = dto.value();
        switch (field) {
            case AMOUNT:
                    try{
                        BigDecimal bigDecimal = new BigDecimal(value);
                        return true;
                    }catch(NumberFormatException ex){
                        return false;
                    }
        
            case DESCRIPTION:
                return value.length() < 40 && value.length() > 2;

            case DATE:
                    try{
                        LocalDate.parse(value);
                        return true;
                    }catch(DateTimeParseException ex){
                        return false;
                    }
        }

        return false;
    }
    
}
