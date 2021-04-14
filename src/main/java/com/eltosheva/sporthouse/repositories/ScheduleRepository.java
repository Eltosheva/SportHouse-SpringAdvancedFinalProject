package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findAllByUsers_Email(String email);
}
