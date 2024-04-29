package com.cnor.expensetracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cnor.expensetracker.entities.User;

public interface UserRepository extends JpaRepository<User, String>{
    
    @Query("""
            SELECT u from User u WHERE u.username = :userName
            """)
    Optional<User> findUserByUsername(String userName);

    @Query("""
            SELECT u from User u WHERE u.username = :userName AND u.id = :userId
            """)
    Optional<User> findUserByUsernameAndId(String userName, String userId);

}
