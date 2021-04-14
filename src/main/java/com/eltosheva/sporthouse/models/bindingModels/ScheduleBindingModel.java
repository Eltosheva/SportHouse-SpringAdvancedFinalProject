package com.eltosheva.sporthouse.models.bindingModels;

import com.eltosheva.sporthouse.models.entities.Place;
import com.eltosheva.sporthouse.models.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ScheduleBindingModel {
    @FutureOrPresent
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private Time startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private Time endTime;
    @NotBlank(message = "Description can not be empty")
    @Size(min = 10, message = "Description can not be less than 10 characters.")
    private String description;
    @NotNull(message = "Place can not be empty")
    private Place places;
}
