package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Role;
import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import com.eltosheva.sporthouse.models.service.ChangeRoleServiceModel;
import com.eltosheva.sporthouse.models.service.RoleServiceModel;
import com.eltosheva.sporthouse.repositories.RoleRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        roleService = new RoleServiceImpl(roleRepository, userRepository, modelMapper);
    }

    @Test
    void getAllRoles() {

        Role admin = new Role();
        admin.setName(RoleEnum.ADMIN);
        Role coach = new Role();
        coach.setName(RoleEnum.COACH);
        Role user = new Role();
        user.setName(RoleEnum.USER);

        when(roleRepository.findAll()).thenReturn(List.of(admin, coach, user));

        List<RoleServiceModel> roles = roleService.getAllRoles();

        assertEquals(3, roles.size());
    }

    @Test
    void changeUserRole() {
        ChangeRoleServiceModel changeRole = new ChangeRoleServiceModel();
        changeRole.setUserId("123");
        changeRole.setRole("ADMIN");

        Role admin = new Role();
        admin.setName(RoleEnum.ADMIN);
        Role coach = new Role();
        coach.setName(RoleEnum.COACH);
        Role urole = new Role();
        urole.setName(RoleEnum.USER);

        User user = new User();
        user.setId("123");
        user.updateRoleSet(List.of(urole));

        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(roleRepository.findById("ADMIN")).thenReturn(Optional.of(coach));

        roleService.changeUserRole(changeRole);

        verify(userRepository, times(1)).saveAndFlush(user);
    }

    @Test
    void changeUserRoleADMIN() {
        ChangeRoleServiceModel changeRole = new ChangeRoleServiceModel();
        changeRole.setUserId("123");
        changeRole.setRole("ADMIN");

        Role admin = new Role();
        admin.setName(RoleEnum.ADMIN);
        Role coach = new Role();
        coach.setName(RoleEnum.COACH);
        Role urole = new Role();
        urole.setName(RoleEnum.USER);

        User user = new User();
        user.setId("123");
        user.updateRoleSet(List.of(urole));

        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(roleRepository.findById("ADMIN")).thenReturn(Optional.of(admin));

        roleService.changeUserRole(changeRole);

        verify(userRepository, times(1)).saveAndFlush(user);
    }

}