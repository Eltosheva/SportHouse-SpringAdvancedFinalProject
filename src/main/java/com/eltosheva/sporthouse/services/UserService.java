package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.CoachTeamServiceModel;
import com.eltosheva.sporthouse.models.service.UserServiceModel;

import java.util.List;

public interface UserService {
    void initUsers();

    void createUser(UserServiceModel userServiceModel);

    void editUser(UserServiceModel userServiceModel);

    UserServiceModel findById(String id);

    UserServiceModel findByEmailAndPassword(String email, String password);

    UserServiceModel findByEmail(String email);

    List<CoachTeamServiceModel> getAllCoaches();

    List<UserServiceModel> getAllUsers();
    void changeStatus(String email);
    Integer getTrainingsCount ();
}
