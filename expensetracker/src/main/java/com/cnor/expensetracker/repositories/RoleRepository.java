package com.cnor.expensetracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cnor.expensetracker.entities.Role;
import com.cnor.expensetracker.entities.RoleType;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    
    @Query("""
            SELECT r FROM Role r WHERE r.name = :roleType
            """)
    Optional<Role> findRoleByName(RoleType roleType);
}
