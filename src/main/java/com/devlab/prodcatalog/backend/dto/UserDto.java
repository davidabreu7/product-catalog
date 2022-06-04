package com.devlab.prodcatalog.backend.dto;

import com.devlab.prodcatalog.backend.entities.User;

import java.util.List;

public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<RoleDto> roles;

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, String email, List<RoleDto> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public UserDto(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        user.getRoles()
                .stream()
                .map(RoleDto::new)
                .forEach(x -> roles.add(x));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}

