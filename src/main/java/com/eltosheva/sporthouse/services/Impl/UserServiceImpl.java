package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Role;
import com.eltosheva.sporthouse.models.entities.Sport;
import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import com.eltosheva.sporthouse.models.service.CoachServiceModel;
import com.eltosheva.sporthouse.models.service.SportsmanServiceModel;
import com.eltosheva.sporthouse.models.service.UserServiceModel;
import com.eltosheva.sporthouse.repositories.RoleRepository;
import com.eltosheva.sporthouse.repositories.SportRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import com.eltosheva.sporthouse.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SportRepository sportRepository;

    @Override
    public void createUser(UserServiceModel userServiceModel) {
        User user = new User();
        modelMapper.map(userServiceModel,user);
        List<Role> roles = new ArrayList<>();
        if (userServiceModel instanceof SportsmanServiceModel) {
            roles.add(roleRepository.findByName(RoleEnum.USER).orElseThrow());

        } else if (userServiceModel instanceof CoachServiceModel) {
            roles.add(roleRepository.findByName(RoleEnum.COACH).orElseThrow());
            Sport coachSport = sportRepository.findById(((CoachServiceModel) userServiceModel).getSportId()).orElseThrow(IllegalArgumentException::new);
            user.setSport(coachSport);
        }
        user.updateRoleSet(roles);
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public UserServiceModel findById(String id) {
       return userRepository.findById(id)
                .map(user ->  modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public UserServiceModel findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user ->  modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<CoachServiceModel> getAllCoaches() {
        return userRepository.findAll()
                 .stream()
                .filter(user -> user.isCoach() && !user.isAdmin())
                .map(user -> modelMapper.map(user, CoachServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void initUsers() {
        if(userRepository.count() == 0) {
            Role admin = new Role();
            admin.setName(RoleEnum.ADMIN);
            Role coach = new Role();
            coach.setName(RoleEnum.COACH);
            Role user = new Role();
            user.setName(RoleEnum.USER);

            roleRepository.saveAll(List.of(admin, coach, user));

            User appAdmin = new User();
            appAdmin.setFirstName("Admin");
            appAdmin.setLastName("Admin");
            appAdmin.setPassword(passwordEncoder.encode("123"));
            appAdmin.setEmail("1@1");
            appAdmin.setPhoneNum("123456789");
            appAdmin.setProfilePictureUrl("https://ih1.redbubble.net/image.161080070.3717/flat,750x1000,075,f.jpg");
            appAdmin.updateRoleSet(List.of(admin, coach, user));

            userRepository.save(appAdmin);
        }
    }
}


