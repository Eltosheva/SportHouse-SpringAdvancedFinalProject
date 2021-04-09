package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoachTeamServiceModel {
    private String firstName;
    private String lastName;
    private String description;
    private String socialMediaUrl;
    private String phoneNum;
    private String imageUrl;
    private String sportName;
}
