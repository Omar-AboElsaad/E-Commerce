package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String role);
}
