package com.edu.ecommerce.mapper;

import com.edu.ecommerce.dto.user.UserDto;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleService roleService;


        public User fromDto(UserDto userDto) {
        var role = roleService.findById(userDto.getRoleId());
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .userRole(role)
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roleId(user.getUserRole().getId())
                .build();
    }
}
