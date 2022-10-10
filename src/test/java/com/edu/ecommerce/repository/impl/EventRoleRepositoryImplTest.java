package com.edu.ecommerce.repository.impl;

import com.edu.ecommerce.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventRoleRepositoryImplTest {

    private static final long ID = 1L;
    private static final String ROLE_NAME = "ADMINISTRATOR";
    private static final String DESCRIPTION = "Description";

    private EventRoleRepositoryImpl eventRoleRepository;

    @BeforeEach
    void setUp() {
        eventRoleRepository = new EventRoleRepositoryImpl();
    }

    @Test
    public void findRoleById_shouldFindUserRole_whenExist() {
        final UserRole userRole = mock(UserRole.class);
        when(userRole.getId()).thenReturn(ID);

        eventRoleRepository.saveRole(userRole);
        final UserRole userRoleActual = eventRoleRepository.findById(ID);

        assertNotNull(userRoleActual);
        assertEquals(userRole, userRoleActual);
    }


    @Test
    void findRoleByName() {
//        final UserRole userRole = mock(UserRole.class);
//        when(userRole.getName()).thenReturn(ROLE_NAME);
//        when(userRole.getId()).thenReturn(ID);
//        when(userRole.getDescription()).thenReturn(DESCRIPTION);
//
//        eventRoleRepository.saveRole(userRole);
//        final UserRole userRoleActual = eventRoleRepository.findByName("ADMINISTRATOR");
//
//        assertNotNull(userRoleActual.getName());
//        assertEquals(userRole, userRoleActual);
    }

    @Test
    void findById() {
    }

    @Test
    void saveRole() {
    }
}