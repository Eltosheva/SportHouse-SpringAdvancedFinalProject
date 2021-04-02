package com.eltosheva.sporthouse.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schedulies")
@Data
@NoArgsConstructor
public class Schedule extends BaseEntity{

    @Column(name = "date_and_time")
    @DateTimeFormat(pattern = " ddd, dd MMMM yyyy HH:mm")
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "place_schedule",
        joinColumns = { @JoinColumn(name = "schedule_id", referencedColumnName = "id")},
        inverseJoinColumns = { @JoinColumn(name = "place_id", referencedColumnName = "id")})
    private Set<Place> places = new HashSet<>();

}
