package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, String> {
}
