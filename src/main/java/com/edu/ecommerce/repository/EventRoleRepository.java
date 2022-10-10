package com.edu.ecommerce.repository;

import com.edu.ecommerce.model.UserRole;

public interface EventRoleRepository {

    UserRole findByName(String roleName);

    UserRole findById(Long id);

    void saveRole(UserRole userRole);

}
