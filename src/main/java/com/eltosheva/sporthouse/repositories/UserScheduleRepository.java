package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule, String> {
    Optional<UserSchedule> findByScheduleId(String id);
}