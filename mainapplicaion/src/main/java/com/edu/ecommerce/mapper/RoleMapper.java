package com.edu.ecommerce.mapper;

import com.edu.ecommerce.dto.role.RoleDto;
import com.edu.ecommerce.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(UserRole role) {
        return RoleDto.builder()
                      .id(role.getId())
                      .name(role.getName())
                      .description(role.getDescription())
                      .build();
    }

    public UserRole fromDto(RoleDto roleDto) {
        return UserRole.builder()
                       .id(roleDto.getId())
                       .name(roleDto.getName())
                       .description(roleDto.getDescription())
                       .build();
    }
}
