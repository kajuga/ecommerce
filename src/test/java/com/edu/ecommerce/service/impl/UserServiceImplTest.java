package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.mapper.SignUpUserMapper;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.model.UserRole;
import com.edu.ecommerce.repository.RoleRepository;
import com.edu.ecommerce.repository.UserRepository;
import com.edu.ecommerce.security.AuthenticatedUser;
import com.edu.ecommerce.service.interfaces.AuthenticationService;
import com.edu.ecommerce.service.interfaces.DataService;
import com.edu.ecommerce.service.interfaces.RoleService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private DataService dataService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthenticatedUser authenticatedUser;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private SignUpUserMapper signUpUserMapper;


//    @Test
//    void addUser() {
////        User user = new User();
//////        UserRole role = new UserRole(1L, "Boss", "litle boss");
////        UserRole role = roleService.create(new UserRole(1L, "Boss", "litle boss"));
////        user = userService.create(new User("Aleksandr", "Fedorov", "alfedorov@mail.com", "qwerty", role ));
//
//    }




//    @Test
//    void update() {
//    }
//
//    @Test
//    void findById() {
//    }
//
//    @Test
//    void findAll() {
//    }
//
//    @Test
//    void findByEmail() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void isUserRoleEquals() {
//    }
//
//    @Test
//    void signIn() {
//    }
}