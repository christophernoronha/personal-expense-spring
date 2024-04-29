package com.cnor.expensetracker.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String>{

    private final String REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        return value.matches(REGEX);
    }    
}
