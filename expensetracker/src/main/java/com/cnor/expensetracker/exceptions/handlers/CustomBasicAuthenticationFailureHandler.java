package com.cnor.expensetracker.exceptions.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.cnor.expensetracker.constants.ErrorCodeConstant;
import com.cnor.expensetracker.dtos.exception.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CustomBasicAuthenticationFailureHandler implements AuthenticationEntryPoint {

    
    private final MessageSource messageSource;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
        
		response.addHeader("content-type", "application/json");
        response.setStatus(400);
        List<ErrorResponseDTO> errors = new ArrayList<>();

        String errorCode = ErrorCodeConstant.BADCREDENTIAL.getErrorCode();
        String errorMessage = messageSource.getMessage(errorCode , null, LocaleContextHolder.getLocale());

        errors.add(new ErrorResponseDTO(errorCode, errorMessage));

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errors);
	}    
}
