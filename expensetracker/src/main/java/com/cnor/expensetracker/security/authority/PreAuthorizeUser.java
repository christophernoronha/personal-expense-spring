package com.cnor.expensetracker.security.authority;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cnor.expensetracker.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PreAuthorizeUser {

    private final UserRepository userRepository;
    
    public boolean isSameUser(String userId){
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.findUserByUsernameAndId(userName, userId)
                .orElseThrow(() ->  new UsernameNotFoundException("User does not exist"));

        return true;
    }
}
