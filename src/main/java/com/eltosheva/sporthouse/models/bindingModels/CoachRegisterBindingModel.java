package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CoachRegisterBindingModel extends UserRegisterBindingModel{
    @NotBlank
    private String sportId;
    @Size(min = 10)
    @NotBlank
    private String description;
    @URL
    private String socialMediaUrl;
}
