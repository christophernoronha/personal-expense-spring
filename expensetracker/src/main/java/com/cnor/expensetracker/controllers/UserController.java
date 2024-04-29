package com.cnor.expensetracker.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnor.expensetracker.dtos.request.userrequest.UserPatchRequestDTO;
import com.cnor.expensetracker.dtos.request.userrequest.UserRequestDTO;
import com.cnor.expensetracker.dtos.userresponse.UserResponseDTO;
import com.cnor.expensetracker.entities.User;
import com.cnor.expensetracker.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(produces = "application/vnd.expensetrack.v1+json", consumes = "application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO requestDTO, HttpServletRequest request){
        User user = userService.createUser(requestDTO);
        String currentURI = request.getRequestURL().append("/").append(user.getId()).toString();
        return ResponseEntity.created(URI.create(currentURI)).build();
    }

    @GetMapping(produces = "application/vnd.expensetrack.v1+json", value = "/{userid}")
    @PreAuthorize("@preAuthorizeUser.isSameUser(#userid)")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("userid") String userid){
        UserResponseDTO user = userService.getUserByUserId(userid);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("@preAuthorizeUser.isSameUser(#userid)")
    @PatchMapping(produces = "application/vnd.expensetrack.v1+json", value = "/{userid}")
    public ResponseEntity<?> updateUserById(@PathVariable("userid") String userid, @Valid  @RequestBody UserPatchRequestDTO patchRequestDTO){
        userService.updateUserById(userid);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') and @preAuthorizeUser.isSameUser(#userid)")
    @DeleteMapping(produces = "application/vnd.expensetrack.v1+json", value = "/{userid}")
    public ResponseEntity<?> deleteByUserId(@PathVariable("userid") String userid){
        userService.deleteUserById(userid);
        return ResponseEntity.noContent().build();
    }

}
