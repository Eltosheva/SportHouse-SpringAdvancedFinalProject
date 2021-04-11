package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Role;
import com.eltosheva.sporthouse.models.entities.Sport;
import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import com.eltosheva.sporthouse.models.service.CoachTeamServiceModel;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        when(userRepository.findUserByEmailAndPassword("1@1", "123")).thenReturn(Optional.ofNullable(appAdmin));
        UserServiceModel user = userService.findByEmailAndPassword("1@1", "123");

        Assertions.assertEquals(appAdmin.getFirstName(), user.getFirstName());
    }

    @Test
    @WithMockUser(value = "1@1")
    void findByEmailThrowIllegalArgsException() {
        when(userRepository.findUserByEmailAndPassword("", "")).thenThrow(IllegalArgumentException.class);
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

    @Test
    void initUsers() {
        Role admin = new Role();
        admin.setName(RoleEnum.ADMIN);
        Role coach = new Role();
        coach.setName(RoleEnum.COACH);
        Role user = new Role();
        user.setName(RoleEnum.USER);


        User appAdmin = new User();
        appAdmin.setFirstName("Admin");
        appAdmin.setLastName("Admin");
        appAdmin.setPassword(passwordEncoder.encode("123456"));
        appAdmin.setEmail("1@1");
        appAdmin.setPhoneNum("123456789");
        appAdmin.setProfilePictureUrl("https://ih1.redbubble.net/image.161080070.3717/flat,750x1000,075,f.jpg");
        appAdmin.updateRoleSet(List.of(admin, coach, user));

        when(userRepository.count()).thenReturn(0L);
        userService.initUsers();
        verify(roleRepository, times(1)).saveAll(List.of(admin, coach, user));
        verify(userRepository, times(1)).save(appAdmin);
    }

    @Test
    void findByEmail_valid() {
        User u = new User();
        u.setEmail("1@1");
        when(userRepository.findByEmail("1@1")).thenReturn(Optional.of(u));
        UserServiceModel userServiceModel = userService.findByEmail("1@1");
        Assertions.assertEquals(userServiceModel.getId(), u.getId());
    }

    @Test
    void findByEmail_invalidArts() {
        when(userRepository.findByEmail("invalidEmail")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.findByEmail("invalidEmail"));
    }

    @Test
    void getAllCoaches() {

        Sport sport = new Sport();
        sport.setName("Basketball");

        Role coach = new Role();
        coach.setName(RoleEnum.COACH);
        Role user = new Role();
        user.setName(RoleEnum.USER);

        User u1 = new User();
        u1.setEmail("1@1");
        u1.updateRoleSet(List.of(coach));
        u1.setSport(sport);

        User u2 = new User();
        u2.setEmail("2@2");
        u2.updateRoleSet(List.of(user));

        User u3 = new User();
        u3.setEmail("3@3");
        u3.updateRoleSet(List.of(coach));
        u3.setSport(sport);

        when(userRepository.findAll()).thenReturn(List.of(u1, u2, u3));

        List<CoachTeamServiceModel> coaches = userService.getAllCoaches();

        assertEquals(2, coaches.size());
        verify(userRepository, times(1)).findAll();
    }
}