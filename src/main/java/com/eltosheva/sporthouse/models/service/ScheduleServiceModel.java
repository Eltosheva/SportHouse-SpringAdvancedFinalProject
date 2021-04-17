package com.eltosheva.sporthouse.models.service;

import com.eltosheva.sporthouse.models.entities.Place;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ScheduleServiceModel {
    private String id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private String placeId;
    private Place place;
}