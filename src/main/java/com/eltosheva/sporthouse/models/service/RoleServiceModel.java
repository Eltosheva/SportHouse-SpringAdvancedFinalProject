package com.eltosheva.sporthouse.models.service;

import com.eltosheva.sporthouse.models.enums.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleServiceModel {
    private String id;
    private RoleEnum name;
    private String description;
}
