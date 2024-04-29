package com.cnor.expensetracker.exceptions.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cnor.expensetracker.constants.ErrorCodeConstant.*;
import com.cnor.expensetracker.dtos.exception.ErrorResponseDTO;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler{

    private final MessageSource messageSource;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorResponseDTO>> handleIllegalArgumentException(MethodArgumentNotValidException ex){
        List<ErrorResponseDTO> errors = new ArrayList<>();
        ex.getBindingResult()
            .getFieldErrors()
            .forEach(error -> errors.add(new ErrorResponseDTO(VALIDATION.getErrorCode(), error.getField().toUpperCase() + "-" + error.getDefaultMessage())));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<List<ErrorResponseDTO>> handleException(Exception ex){
        List<ErrorResponseDTO> errors = new ArrayList<>();

        String errorCode = INTERNAL_SERVER_ERROR.getErrorCode();
        String errorMessage = messageSource.getMessage(errorCode,null, LocaleContextHolder.getLocale());
        ErrorResponseDTO error = new ErrorResponseDTO(errorCode, errorMessage);
        errors.add(error);

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorResponseDTO>> handleUserNameNotFoundException(UsernameNotFoundException ex){
        List<ErrorResponseDTO> errors = new ArrayList<>();
        ErrorResponseDTO error = new ErrorResponseDTO(FORBIDDEN.getErrorCode(), messageSource.getMessage(FORBIDDEN.getErrorCode(), null, LocaleContextHolder.getLocale()));
        errors.add(error);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
}
