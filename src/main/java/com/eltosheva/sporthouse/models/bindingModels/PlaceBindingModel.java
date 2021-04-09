package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class PlaceBindingModel {
    @NotBlank(message = "Place must have a name")
    private String name;
    @NotBlank(message = "Place must have city")
    private String city;
    @NotBlank(message = "Place must have current address")
    private String address;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workFrom;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workTo;
    private String imageUrl;
    @NotBlank(message = "Enter phone number for this place")
    private String phone;
    private String description;
}
