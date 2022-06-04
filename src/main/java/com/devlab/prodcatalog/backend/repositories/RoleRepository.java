package com.devlab.prodcatalog.backend.repositories;

import com.devlab.prodcatalog.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
