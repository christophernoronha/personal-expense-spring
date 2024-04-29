package com.cnor.expensetracker.validators;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.cnor.expensetracker.dtos.request.userrequest.UserPatchRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UserPatchRequestValidator implements ConstraintValidator<UserPatchRequestValidation, UserPatchRequestDTO>{

    private final String REGEX =  "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";

	@Override
	public boolean isValid(UserPatchRequestDTO dto, ConstraintValidatorContext context){

        var patchType = dto.patchType();
        String value = dto.value();

        switch (patchType) {
            case ROLE:
                if(value.equals("USER") || value.equals("ADMIN")){
                    return true;
                }
                return false;

            case PASSWORD:
                return value.matches(REGEX);
            
        }
        return false;
	}

    
    
}
