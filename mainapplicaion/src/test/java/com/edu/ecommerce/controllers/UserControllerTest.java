package com.edu.ecommerce.controllers;

import com.edu.ecommerce.config.MvcTestConfiguration;
import com.edu.ecommerce.configuration.SecurityConfig;
import com.edu.ecommerce.dto.user.UserDto;
import com.edu.ecommerce.mapper.UserMapper;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.model.UserRole;
import com.edu.ecommerce.service.interfaces.RoleService;
import com.edu.ecommerce.service.interfaces.UserService;
import com.edu.ecommerce.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    private static final String REQUEST_CREATE_1 = BASE_REQUEST + "user_create_1.json";
    private static final String REQUEST_UPDATE_1 = BASE_REQUEST + "user_update_new_1.json";

    private static final String RESPONSE_CREATE_1 = BASE_RESPONSE + "user_create_1.json";
    private static final String RESPONSE_UPDATE_OLD_1 = BASE_RESPONSE + "user_update_old_1.json";
//    private static final String RESPONSE_UPDATE_1 = BASE_RESPONSE + "user_update_old_1.json";
    private static final String RESPONSE_GET_1 = BASE_RESPONSE + "user_get_1.json";
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


    @Test
    void getUserByIdSuccessTest() throws Exception {

        final UserDto response = TestUtil.readJsonResource(RESPONSE_GET_1, UserDto.class);
        final String expected = TestUtil.write(response);

            when(roleService.findById(anyLong())).thenAnswer(invocationOnMock -> {
                var role = new UserRole();
                role.setId(invocationOnMock.getArgument(0, Long.class));
                return role;
            });

        doReturn(mapper.fromDto(response))
                .when(userService)
                .findById(2L);

        this.mockMvc
                .perform(get("/users/{id}", 2L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }


    @Test
    void createUserSuccessTest() throws Exception {
            final UserDto request = TestUtil.readJsonResource(REQUEST_CREATE_1, UserDto.class);
            final UserDto response = TestUtil.readJsonResource(RESPONSE_CREATE_1, UserDto.class);
            final String expected = TestUtil.write(response);

        when(roleService.findById(anyLong())).thenAnswer(invocationOnMock -> {
            var role = new UserRole();
            role.setId(invocationOnMock.getArgument(0, Long.class));
            return role;
        });

            doReturn(mapper.fromDto(response))
                    .when(userService)
                    .create(any(User.class));

            this.mockMvc
                    .perform(MockMvcRequestBuilders.post("/users")
                            .with(csrf())
                            .content(asJsonString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(print())
                    .andExpect(content().string(expected))
                    .andExpect(status().isCreated());
        }

//        @Test
//        void updateUserSuccessTest() throws Exception {
//            final UserDto request = TestUtil.readJsonResource(REQUEST_UPDATE_1, UserDto.class);
//            final UserDto response = TestUtil.readJsonResource(RESPONSE_UPDATE_OLD_1, UserDto.class);
//            final String expected = TestUtil.write(response);
//
//            when(roleService.findById(anyLong())).thenAnswer(invocationOnMock -> {
//                var role = new UserRole();
//                role.setId(invocationOnMock.getArgument(0, Long.class));
//                return role;
//            });
//
//            this.mockMvc
//                    .perform(MockMvcRequestBuilders.put("/users/{id}", 2L)
//                            .with(csrf())
//                            .content(asJsonString(request))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andDo(MockMvcResultHandlers.print())
//                    .andDo(print())
//                    .andExpect(content().string(expected))
//                    .andExpect(status().isOk());
//
//        }



    static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}