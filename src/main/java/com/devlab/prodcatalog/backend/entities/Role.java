package com.devlab.prodcatalog.backend.entities;

import com.devlab.prodcatalog.backend.dto.RoleDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_role")
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private final Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Role(RoleDto roleDto) {
        id = roleDto.getId();
        authority = roleDto.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }
}
