package com.cnor.expensetracker.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UserPatchRequestValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UserPatchRequestValidation {
    
    String message() default "Invalid Patch request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
