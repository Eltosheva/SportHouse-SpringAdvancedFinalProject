package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findById(String id);
  Optional<User> findByEmailAndPassword(String email, String password);
  Optional<User> findByEmail(String email);
  @Query("SELECT u.firstName as firstName, u.profilePictureUrl as profilePicUrl FROM User u WHERE u.email = ?1")
  Map<String, String> getUserData(String email);
}
