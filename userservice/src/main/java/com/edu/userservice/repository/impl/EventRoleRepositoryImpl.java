package com.edu.userservice.repository.impl;

import com.edu.userservice.model.UserRole;
import com.edu.userservice.repository.EventRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EventRoleRepositoryImpl implements EventRoleRepository {

    private final ConcurrentHashMap<Long, UserRole> storage;

    public EventRoleRepositoryImpl() {
        storage = new ConcurrentHashMap<>();
    }


    @Override
    public UserRole findByName(String roleName) {
        return storage.get(roleName);
    }

    @Override
    public UserRole findById(Long id) {
        return storage.get(id);
    }

    @Override
    public void saveRole(UserRole userRole) {
        storage.put(userRole.getId(), userRole);
    }
}
