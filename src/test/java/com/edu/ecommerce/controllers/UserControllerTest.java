package com.edu.ecommerce.controllers;

import com.edu.ecommerce.config.MvcTestConfiguration;
import com.edu.ecommerce.configuration.SecurityConfig;
import com.edu.ecommerce.dto.category.CategoryDto;
import com.edu.ecommerce.dto.user.UserDto;
import com.edu.ecommerce.mapper.CategoryMapper;
import com.edu.ecommerce.mapper.UserMapper;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.model.UserRole;
import com.edu.ecommerce.service.impl.RoleServiceImpl;
import com.edu.ecommerce.service.interfaces.RoleService;
import com.edu.ecommerce.service.interfaces.UserService;
import com.edu.ecommerce.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@WithMockUser
@WebMvcTest(
        controllers = UserController.class,
        useDefaultFilters = false,
        excludeAutoConfiguration = {
                SecurityConfig.class
        }
)
@Import({UserController.class, MvcTestConfiguration.class})
@AutoConfigureMockMvc
@ActiveProfiles("mock-test")
class UserControllerTest {

    @MockBean
    private UserService userService;



    @Autowired
    private MockMvc mockMvc;

    private final RoleService roleService = mock(RoleService.class);
    private final UserMapper mapper = new UserMapper(roleService);


    private static final String BASE_REQUEST = "json/request/";
    private static final String BASE_RESPONSE = "json/response/";

    private static final String RESPONSE_GET_3 = BASE_RESPONSE + "user_get_3.json";


        @Test
    void getUsersTest() throws Exception {
        final List<UserDto> response = TestUtil.readJsonResourceToList(RESPONSE_GET_3, UserDto.class);
        final String expected = TestUtil.write(response);

        when(roleService.findById(anyLong())).thenAnswer(invocationOnMock -> {
            var role = new UserRole();
            role.setId(invocationOnMock.getArgument(0, Long.class));
            return role;
        });

        doReturn(response.stream().map(mapper::fromDto).collect(Collectors.toList()))
                .when(userService)
                .findAll();

        this.mockMvc
                .perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }


}