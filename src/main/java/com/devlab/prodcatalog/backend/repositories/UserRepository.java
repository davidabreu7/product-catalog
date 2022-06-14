package com.devlab.prodcatalog.backend.repositories;

import com.devlab.prodcatalog.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    UserDetails findUserByEmail(String email);
}
