package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    Optional<Subscription> findById(String id);
}
