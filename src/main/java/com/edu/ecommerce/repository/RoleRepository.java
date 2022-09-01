package com.edu.ecommerce.repository;

import com.edu.ecommerce.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByName(String roleName);
    Optional<UserRole> findById(Long id);
}
