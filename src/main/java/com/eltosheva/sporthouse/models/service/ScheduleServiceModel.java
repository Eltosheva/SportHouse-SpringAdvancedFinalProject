package com.eltosheva.sporthouse.models.service;

import com.eltosheva.sporthouse.models.entities.Place;
import com.eltosheva.sporthouse.models.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class ScheduleServiceModel {
    private LocalDate date;
    private Time startTime;
    private Time endTime;
    private String description;
    private Set<User> users;
    private Place places;
}
