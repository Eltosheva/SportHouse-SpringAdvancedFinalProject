package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoachServiceModel extends UserServiceModel{
    private String sportId;
    private String description;
    private String socialMediaUrl;
}
