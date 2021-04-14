package com.eltosheva.sporthouse.models.entities;

import jdk.jfr.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
public class Schedule extends BaseEntity{

    @Column(name = "date")
    @DateTimeFormat(pattern = "E, dd MMMM yyyy")
    private LocalDate date;

    @Column(name = "start_time")
    @Timestamp("HH:mm")
    private Time startTime;

    @Column(name = "end_time")
    @Timestamp("HH:mm")
    private Time endTime;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(name = "schedules_users",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "place_schedule",
        joinColumns = { @JoinColumn(name = "schedule_id", referencedColumnName = "id")},
        inverseJoinColumns = { @JoinColumn(name = "place_id", referencedColumnName = "id")})
    private Set<Place> places;

}
