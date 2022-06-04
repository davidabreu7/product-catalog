package com.devlab.prodcatalog.backend.util;

import com.devlab.prodcatalog.backend.entities.Role;
import com.devlab.prodcatalog.backend.entities.User;

import java.util.Set;

public class UserFactory {

    public static User createUser(){
        return new User("Bob","Wilson", "bob.wilson@email.com", "password",
                Set.of(new Role(1L,"ROLE_OPERATOR"), new Role(2L,"ROLE_ADMIN")));
    }
}
