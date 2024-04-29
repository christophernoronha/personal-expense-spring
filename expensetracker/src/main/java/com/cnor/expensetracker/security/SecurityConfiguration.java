package com.cnor.expensetracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cnor.expensetracker.exceptions.handlers.CustomBasicAuthenticationFailureHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    
    @Autowired
    private  MessageSource messageSource;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
        .httpBasic(basic -> basic.authenticationEntryPoint(new CustomBasicAuthenticationFailureHandler(messageSource)))
            .sessionManagement(session -> session.disable())
            .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, "/users").permitAll() 
                    .anyRequest().authenticated())
            .csrf(csrf -> csrf.disable());
            
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
