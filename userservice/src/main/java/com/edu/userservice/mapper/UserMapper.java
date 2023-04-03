package com.edu.userservice.mapper;

import com.edu.userservice.dto.user.UserDto;
import com.edu.userservice.model.User;
import com.edu.userservice.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleService roleService;


        public User fromDto(UserDto userDto) {
        var role = roleService.findById(userDto.getRoleId());
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .userRole(role)
                .password(userDto.getPassword())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roleId(user.getUserRole().getId())
                .password(user.getPassword())
                .build();
    }
}
