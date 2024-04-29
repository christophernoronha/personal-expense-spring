package com.cnor.expensetracker.dtos.request.userrequest;

import com.cnor.expensetracker.validators.UsernameValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record UserRequestDTO(@NotNull @NotBlank @NotEmpty @Email(message = "{email.invalid.message}") @UsernameValidation String username,@NotNull @NotBlank @NotEmpty @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$",
message = "{password.invalid.message}") String password) {}
