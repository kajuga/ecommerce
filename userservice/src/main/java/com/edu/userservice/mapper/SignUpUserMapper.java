package com.edu.userservice.mapper;

import com.edu.userservice.dto.user.SignUpDto;
import com.edu.userservice.dto.user.UserDto;
import com.edu.userservice.model.User;
import com.edu.userservice.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SignUpUserMapper {

    private final RoleService roleService;


        public User fromDto(SignUpDto signUpDto) {
        var role = roleService.findById(signUpDto.getRoleId());
        return User.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .userRole(role)
                .password(signUpDto.getPassword())
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