package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.UserOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderHistoryRepository extends JpaRepository<UserOrderHistory, String> {
}
