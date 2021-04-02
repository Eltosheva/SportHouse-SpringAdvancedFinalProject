package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserServiceModel {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNum;
    private String imageUrl;
    private String profilePictureUrl;
    private boolean isActive;
}