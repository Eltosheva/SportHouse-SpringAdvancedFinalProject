package com.eltosheva.sporthouse.models.entities;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "places")
@Data
@NoArgsConstructor
public class Place extends BaseEntity {

    @Expose
    @Column(name = "name")
    private String name;

    @Expose
    @Column(name = "city")
    private String city;

    @Expose
    @Column(name = "address")
    private String address;

    @Expose
    @Column(name = "work_from")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workFrom;

    @Expose
    @Column(name = "work_to")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workTo;

    @Expose
    @Column(name = "image_url")
    private String imageUrl;

    @Expose
    @Column(name = "phone")
    private String phone;

    @Expose
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
