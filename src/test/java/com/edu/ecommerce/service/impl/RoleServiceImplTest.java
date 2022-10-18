package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.config.MvcTestConfiguration;
import com.edu.ecommerce.configuration.SecurityConfig;
import com.edu.ecommerce.controllers.RoleController;
import com.edu.ecommerce.model.UserRole;
import com.edu.ecommerce.service.interfaces.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


@ExtendWith(SpringExtension.class)
@WithMockUser
@WebMvcTest(
        controllers = RoleController.class,
        useDefaultFilters = false,
        excludeAutoConfiguration = {
                SecurityConfig.class
        }
)
@Import({RoleController.class, MvcTestConfiguration.class})
@AutoConfigureMockMvc(/*addFilters = false*/)
class RoleServiceImplTest {
    private final long ID = 666L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RoleService roleService;

    @Test
    void findAllUserRoles() throws Exception {
        when(roleService.findAll()).thenReturn(Arrays.asList(
                new UserRole(1L, "ADMINISTRATOR", "ad"),
                new UserRole(2L, "MANAGER", "ma"),
                new UserRole(3L, "SPECIALIST", "sp"),
                new UserRole(4L, "EXTERNAL_", "ex")
        ));

        assertEquals(4, roleService.findAll().size());
        mockMvc.perform(MockMvcRequestBuilders.get("/role"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[0].description", is("ad")))
                .andExpect(jsonPath("$[1].name", is("MANAGER")))
                .andExpect(jsonPath("$[*].id",containsInAnyOrder(1,2,3,4)))
                .andExpect(jsonPath("$[*].description", containsInAnyOrder("ex", "ad", "sp", "ma")))
                .andReturn();
    }

    @Test
    void findRoleById() {
        UserRole expectedRole = new UserRole(ID, "ADMINISTRATOR", "ad");
        when(roleService.findById(ID)).thenReturn(new UserRole(ID, "ADMINISTRATOR", "ad"));
        assertEquals(expectedRole, roleService.findById(ID));
        assertEquals("ad", roleService.findById(ID).getDescription());

    }

    @Test
    void createRole() throws Exception {
        UserRole expectedRole = new UserRole(ID, "ADMINISTRATOR", "ad");
        when(roleService.create(new UserRole(ID, "ADMINISTRATOR", "ad"))).thenReturn(expectedRole);
        assertEquals(expectedRole, roleService.create(expectedRole));

    }

    @Test
    public void testUpdate() {
        UserRole expectedRole = new UserRole(ID, "ADMINISTRATOR", "ad");
        when(roleService.findById(ID)).thenReturn(expectedRole);
        when(roleService.update(ID,expectedRole)).thenReturn(expectedRole);
        assertEquals(expectedRole, roleService.update(ID, expectedRole));
    }

    @Test
    public void testDelete() {
        UserRole expectedRole = new UserRole(ID, "ADMINISTRATOR", "ad");
        when(roleService.findById(ID)).thenReturn(new UserRole(ID, "ADMINISTRATOR", "ad"));
        doNothing().when(roleService).delete(ID);
        roleService.delete(ID);
        verify(roleService).delete(ID);
    }

}