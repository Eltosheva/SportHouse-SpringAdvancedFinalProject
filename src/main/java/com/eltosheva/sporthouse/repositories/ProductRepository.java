package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findById(String id);
    List<Product> findAllBySport(String sportId);
}
