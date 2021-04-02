package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, String> {
}
