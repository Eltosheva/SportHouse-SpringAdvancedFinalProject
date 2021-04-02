package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportServiceModel {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private Boolean isActive;
}
