package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.service.ScheduleListServiceModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserScheduleServiceImplTest {

    UserScheduleServiceImpl userScheduleService;

    @Mock
    private EntityManagerFactory emf;
    @Mock
    EntityManager em;

    @BeforeEach
    void setUp() {
        userScheduleService = new UserScheduleServiceImpl(emf);
        User u1 = new User();
        u1.setId("123");
        u1.setEmail("1@1");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findAllUserSchedules() {
    }
}