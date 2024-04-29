package com.cnor.expensetracker.services;


import java.util.Arrays;

import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cnor.expensetracker.dtos.request.userrequest.OpType;
import com.cnor.expensetracker.dtos.request.userrequest.UserPatchRequestDTO;
import com.cnor.expensetracker.dtos.request.userrequest.UserRequestDTO;
import com.cnor.expensetracker.dtos.userresponse.UserResponseDTO;
import com.cnor.expensetracker.entities.Role;
import com.cnor.expensetracker.entities.RoleType;
import com.cnor.expensetracker.entities.User;
import com.cnor.expensetracker.repositories.RoleRepository;
import com.cnor.expensetracker.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequestDTO dto){
       User user = userDTOtoModelMapper(dto);
       Role role = roleRepository.findRoleByName(RoleType.ROLE_USER).
                orElseThrow(() -> new IllegalArgumentException(RoleType.ROLE_USER + " does not exist in database"));
       user.setRole(role);
       
       return userRepository.save(user);
    }

    public UserResponseDTO getUserByUserId(String userId){
        return userRepository.findById(userId)
            .map((user) -> new UserResponseDTO(user.getId(), user.getUsername()))
            .orElseThrow(() -> new UsernameNotFoundException(userId + "  does not exsist"));
     }

    public void updateUserById(String userId, UserPatchRequestDTO patchDTO){
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UsernameNotFoundException(userId + "  does not exsist"));

        switch (patchDTO.patchType()) {
            case PASSWORD:
                user.setPassword(patchDTO.value());
                break;
        
            case ROLE:
                setRole(user, patchDTO.value());
                break;
        }

        userRepository.save(user);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     private void setRole(User user, String value){
        RoleType roleType = value == "ADMIN" ? RoleType.ROLE_ADMIN : RoleType.ROLE_USER;
        var role = roleRepository.findRoleByName(roleType).get();
        user.setRole(role);
     }

     public void deleteUserById(String userId){
        userRepository.deleteById(userId);
     }
    
    
    private User userDTOtoModelMapper(UserRequestDTO dto){
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        return user;
    }
    
}
