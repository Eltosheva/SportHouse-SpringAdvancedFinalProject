package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.*;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import com.eltosheva.sporthouse.models.service.*;
import com.eltosheva.sporthouse.repositories.RoleRepository;
import com.eltosheva.sporthouse.repositories.SportRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import com.eltosheva.sporthouse.repositories.UserScheduleRepository;
import com.eltosheva.sporthouse.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserScheduleRepository userScheduleRepository;

    @Override
    public void createUser(UserServiceModel userServiceModel) {
        User user = new User();
        modelMapper.map(userServiceModel, user);
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
        user.setAvailableTraining(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void editUser(UserServiceModel userServiceModel) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        user.setFirstName(userServiceModel.getFirstName());
        user.setLastName(userServiceModel.getLastName());
        user.setPhoneNum(userServiceModel.getPhoneNum());
        if (!"".equals(userServiceModel.getPassword())) user.setPassword(userServiceModel.getPassword());
        user.setIsActive(true);

        if (user.isUser()) {
            user.setAge(userServiceModel.getAge());
            user.setTargetWeight(userServiceModel.getTargetWeight());
            user.setProfilePictureUrl(userServiceModel.getProfilePictureUrl());
        } else if (user.isCoach() && !user.isAdmin()) {
            user.setSocialMediaUrl(userServiceModel.getSocialMediaUrl());
            user.setDescription(userServiceModel.getDescription());
            Sport coachSport = sportRepository.findById(((CoachServiceModel) userServiceModel).getSportId()).orElseThrow(IllegalArgumentException::new);
            user.setSport(coachSport);
        }
        if(user.isAdmin()) {
            Sport adminSport = sportRepository.findById(((AdminServiceModel) userServiceModel).getSportId()).orElseThrow(IllegalArgumentException::new);
            user.setSport(adminSport);
        }

        userRepository.saveAndFlush(user);
    }

    @Override
    public UserServiceModel findById(String id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public UserServiceModel findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException();
        return modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<CoachTeamServiceModel> getAllCoaches() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.isCoach() && !user.isAdmin() && user.getIsActive())
                .map(user -> {
                    CoachTeamServiceModel coach = modelMapper.map(user, CoachTeamServiceModel.class);
                    coach.setSportName(user.getSport().getName());
                    return coach;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserManagementServiceModel> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                .map(user -> modelMapper.map(user, UserManagementServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void changeStatus(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
        user.setIsActive(!user.getIsActive());
        userRepository.saveAndFlush(user);
    }

    @Override
    public Integer getTrainingsCount() {
        return userRepository.getTrainingCount(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public void addTrainingToSchedule(String id) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(IllegalArgumentException::new);
        UserSchedule userSchedule = new UserSchedule();
        userSchedule.setUser(user);
        userSchedule.setScheduleId(id);
        userScheduleRepository.saveAndFlush(userSchedule);

        user.setAvailableTraining(user.getAvailableTraining() - 1);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void initUsers() {
        if (userRepository.count() == 0) {
            Role admin = new Role();
            admin.setName(RoleEnum.ADMIN);
            Role coach = new Role();
            coach.setName(RoleEnum.COACH);
            Role user = new Role();
            user.setName(RoleEnum.USER);

            roleRepository.saveAll(List.of(admin, coach, user));

            User appAdmin = new User();
            appAdmin.setIsActive(true);
            appAdmin.setFirstName("Admin");
            appAdmin.setLastName("Admin");
            appAdmin.setPassword(passwordEncoder.encode("123"));
            appAdmin.setEmail("1@1");
            appAdmin.setPhoneNum("123456789");
            appAdmin.setAvailableTraining(0);
            appAdmin.setProfilePictureUrl("https://ih1.redbubble.net/image.161080070.3717/flat,750x1000,075,f.jpg");
            appAdmin.updateRoleSet(List.of(admin, coach, user));
            appAdmin.setSport(sportRepository.findAll().stream().findFirst().get());

            userRepository.save(appAdmin);
        }
    }
}


