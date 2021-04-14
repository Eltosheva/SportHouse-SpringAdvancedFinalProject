package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.Role;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(RoleEnum role);
}
