package com.eltosheva.sporthouse.models.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_schedules")
public class UserSchedule extends BaseEntity {

    @ManyToOne
    private User user;

    @Column(name="schedule_id")
    private String scheduleId;
}