package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Role;
import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import com.eltosheva.sporthouse.models.service.UserServiceModel;
import com.eltosheva.sporthouse.repositories.RoleRepository;
import com.eltosheva.sporthouse.repositories.SportRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SportRepository sportRepository;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl(userRepository, new ModelMapper(),
                roleRepository, passwordEncoder, sportRepository);
    }

    @Test
    @WithMockUser(value = "1@1")
    void findByIdWithValidUser() {
        User appAdmin = getUserData();
        when(userRepository.findById(appAdmin.getId())).thenReturn(Optional.of(appAdmin));
        UserServiceModel user = userService.findById(appAdmin.getId());

        Assertions.assertEquals(appAdmin.getEmail(), user.getEmail());
    }

    @Test
    @WithMockUser(value = "1@1")
    void findByIdThrowException() {
        when(userRepository.findById("invalidId")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.findById("invalidId"));
    }


    @Test
    @WithMockUser(value = "1@1")
    void findByEmailWithValidUser() {
        User appAdmin = getUserData();
        when(userRepository.findByEmailAndPassword("1@1", "123")).thenReturn(Optional.ofNullable(appAdmin));
        UserServiceModel user = userService.findByEmailAndPassword("1@1", "123");

        Assertions.assertEquals(appAdmin.getFirstName(), user.getFirstName());
    }

    @Test
    @WithMockUser(value = "1@1")
    void findByEmailThrowIllegalArgsException() {
        when(userRepository.findByEmailAndPassword("", "")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.findByEmailAndPassword("",""));
    }

    private User getUserData() {
        Role admin = new Role();
        admin.setName(RoleEnum.ADMIN);
        Role coach = new Role();
        coach.setName(RoleEnum.COACH);
        Role user = new Role();
        user.setName(RoleEnum.USER);

        User appAdmin = new User();
        appAdmin.setId("6676f102-a4d2-4f1a-b937-2a3038e9a656");
        appAdmin.setFirstName("Admin");
        appAdmin.setLastName("Admin");
        appAdmin.setPassword(passwordEncoder.encode("123"));
        appAdmin.setEmail("1@1");
        appAdmin.setPhoneNum("1234567890");
        appAdmin.setProfilePictureUrl("https://ih1.redbubble.net/image.161080070.3717/flat,750x1000,075,f.jpg");
        appAdmin.updateRoleSet(List.of(admin, coach, user));

        return appAdmin;
    }
}