package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CoachProfileBindingModel extends ProfileBindingModel {
    @NotBlank
    private String sportId;
    @Size(min = 10)
    @NotBlank
    private String description;
    @URL
    private String socialMediaUrl;
}
