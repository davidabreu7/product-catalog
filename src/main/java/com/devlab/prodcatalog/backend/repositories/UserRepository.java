package com.devlab.prodcatalog.backend.repositories;

import com.devlab.prodcatalog.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    @Query("select u from User u " +
            "JOIN FETCH u.roles " +
            "where u.email = :email")
    UserDetails findUserByEmail(String email);
}
