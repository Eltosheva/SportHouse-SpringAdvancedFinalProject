package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.Role;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(RoleEnum role);
}
