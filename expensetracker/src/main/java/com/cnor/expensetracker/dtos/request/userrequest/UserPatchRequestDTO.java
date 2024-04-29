package com.cnor.expensetracker.dtos.request.userrequest;

import jakarta.validation.constraints.Pattern;


public record UserPatchRequestDTO(@Pattern(regexp = "PASSWORD|ROLE", message = "Valid values are password") UserPatchType patchType, String value) {}  
