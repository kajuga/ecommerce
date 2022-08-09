package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.exceptions.CrmException;
import com.edu.ecommerce.exceptions.ResourceNotFoundException;
import com.edu.ecommerce.model.UserRole;
import com.edu.ecommerce.repository.RoleRepository;
import com.edu.ecommerce.service.interfaces.RoleService;
import com.edu.ecommerce.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The service implementation to the maintain operations with user role.
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserService userService;

    public RoleServiceImpl(RoleRepository roleRepository, @Lazy UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserRole> findAll() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public UserRole findById(Long id) {
        return roleRepository.findById(id)
                             .orElseThrow(() -> new ResourceNotFoundException(String.format("Role with id: %s not found!", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public UserRole findByUserEmail(String email) {
//        User user = userService.findByEmail(email);
//        return user.getUserRole();
        return new UserRole();
    }

    @Transactional(readOnly = true)
    @Override
    public UserRole findByName(String name) {
        return roleRepository.findByName(name)
                             .orElseThrow(() -> new CrmException(String.format("User role name = %s not found.", name)));
    }

    @Transactional
    @Override
    public UserRole create(UserRole role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public UserRole update(Long id, UserRole role) {
        return roleRepository.save(updateRoleFields(findById(id), role));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        UserRole role = findById(id);
        roleRepository.delete(role);
    }

    private UserRole updateRoleFields(UserRole findRole, UserRole request) {
        return UserRole.builder()
                       .id(findRole.getId())
                       .name(Optional.ofNullable(request.getName()).orElse(findRole.getName()))
                       .description(Optional.ofNullable(request.getDescription()).orElse(findRole.getDescription()))
                       .build();
    }
}
