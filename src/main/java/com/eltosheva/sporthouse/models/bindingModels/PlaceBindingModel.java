package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class PlaceBindingModel {
    private String id;
    @NotBlank(message = "Place must have a name.")
    @Size(min = 3, max = 20, message = "Place name must be between 3 and 20 characters.")
    private String name;
    @NotBlank(message = "Place must have city.")
    @Size(min = 3, max = 20, message = "City must be between 3 and 20 characters.")
    private String city;
    @NotBlank(message = "Place must have current address.")
    @Size(min = 3, max = 30, message = "Address must be between 3 and 20 characters.")
    private String address;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workFrom;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workTo;
    @URL(message = "You must provide a valid URL address.")
    private String imageUrl;
    @NotBlank(message = "Enter phone number for this place.")
    @Size(min = 8, message = "Phone number must be at least 8 numbers.")
    private String phone;
    @Size(min = 10, message = "Description can not be less than 10 characters.")
    @NotBlank(message = "Description can not be empty")
    private String description;
    private Boolean isActive;
}
