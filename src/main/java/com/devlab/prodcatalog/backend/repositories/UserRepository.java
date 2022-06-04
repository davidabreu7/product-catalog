package com.devlab.prodcatalog.backend.repositories;

import com.devlab.prodcatalog.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
