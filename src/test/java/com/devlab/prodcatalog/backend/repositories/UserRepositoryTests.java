package com.devlab.prodcatalog.backend.repositories;

import com.devlab.prodcatalog.backend.entities.Role;
import com.devlab.prodcatalog.backend.entities.User;
import com.devlab.prodcatalog.backend.util.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;
    private User newUser;
    Long validId;
    Long InvalidId;

    @BeforeEach
    public void beforeEach() throws Exception {
        validId = 2L;
        InvalidId = 100L;
        newUser = UserFactory.createUser();
    }

    @Test
    public void saveShouldReturnNewUser() throws Exception {
        User user = userRepository.save(newUser);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getFirstName(), "Bob");
        Assertions.assertEquals(user.getId(), 3);
    }

    @Test
    public void userShouldReturnRoles() throws Exception {
        User user = userRepository.getById(validId);
        Set<Role> roles = user.getRoles();
        Assertions.assertNotNull(roles);
    }
}
