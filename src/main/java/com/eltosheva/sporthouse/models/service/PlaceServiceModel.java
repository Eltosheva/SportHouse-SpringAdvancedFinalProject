package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class PlaceServiceModel {
    private String id;
    private String name;
    private String city;
    private String address;
    private LocalTime workFrom;
    private LocalTime workTo;
    private String imageUrl;
    private String phone;
    private String description;
    private boolean isActive;
}
