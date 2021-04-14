package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserManagementServiceModel {
    private String id;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String email;
    private String phoneNum;
    private boolean isActive;
    private Set<RoleServiceModel> roles;
}
