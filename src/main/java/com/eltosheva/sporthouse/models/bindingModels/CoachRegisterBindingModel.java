package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CoachRegisterBindingModel extends UserRegisterBindingModel{
    @NotBlank(message = "Must select a sport.")
    private String sportId;
    @NotBlank(message = "Description can not be empty")
    @Size(min = 10, message = "Description can not be less than 10 characters.")
    private String description;
    @URL(message = "Please provide a valid URL address.")
    private String socialMediaUrl;
}
