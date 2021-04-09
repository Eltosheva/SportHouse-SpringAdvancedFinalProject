package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SportBindingModel {
    @NotBlank(message = "Field must have name of sport")
    private String name;
    private String description;
    private String imageUrl;
    private Boolean isActive;
}
