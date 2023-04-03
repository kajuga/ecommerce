package com.edu.userservice.repository;

import com.edu.userservice.model.UserRole;

public interface EventRoleRepository {

    UserRole findByName(String roleName);

    UserRole findById(Long id);

    void saveRole(UserRole userRole);

}
