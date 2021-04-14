package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SportBindingModel {
    private String id;
    @NotBlank(message = "Field must have name of sport")
    @Size(min = 3, max = 20, message = "Sport name must be between 3 and 20 characters.")
    private String name;
    @NotBlank(message = "Description can not be empty")
    @Size(min = 10, message = "Description can not be less than 10 characters.")
    private String description;
    @URL(message = "You must provide a valid URL address.")
    private String imageUrl;
    private Boolean isActive;
}
