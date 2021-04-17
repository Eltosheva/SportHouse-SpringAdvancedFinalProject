package com.eltosheva.sporthouse.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
public class Schedule extends BaseEntity{

    @Column(name = "date")
    @DateTimeFormat(pattern = "E, dd MMMM yyyy")
    private LocalDate date;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
