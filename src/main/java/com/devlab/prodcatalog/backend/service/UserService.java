package com.devlab.prodcatalog.backend.service;

import com.devlab.prodcatalog.backend.dto.UserDto;
import com.devlab.prodcatalog.backend.dto.UserInsertDto;
import com.devlab.prodcatalog.backend.entities.Role;
import com.devlab.prodcatalog.backend.entities.User;
import com.devlab.prodcatalog.backend.exceptions.DatabaseIntegrityException;
import com.devlab.prodcatalog.backend.exceptions.ResourceNotFoundException;
import com.devlab.prodcatalog.backend.repositories.RoleRepository;
import com.devlab.prodcatalog.backend.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(id)));
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (
                EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Category id: %d not found".formatted(id));
        } catch (
                DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Database Error");
        }
    }

    @Transactional
    public UserDto insert(UserInsertDto userInsertDto) {
        Set<Role> roles = DtoToRole(userInsertDto);
        User user = new User(userInsertDto, roles);
        user.setPassword(passwordEncoder.encode(userInsertDto.getPassword()));
        return new UserDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateById(Long id, UserDto dto) {
        User user = dtoToUser(id, dto);
        return new UserDto(userRepository.save(user));
    }

    private User dtoToUser(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(id)));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        Set<Role> roles = DtoToRole(dto);
        user.getRoles().clear();
        roles.forEach(x -> user.getRoles().add(x));
        return user;
    }

    private Set<Role> DtoToRole(UserDto userDto) {
        return userDto.getRoles()
                .stream()
                .map(roleDto -> roleRepository.findById(roleDto.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Role id: %d not found".formatted(roleDto.getId()))))
                .collect(Collectors.toSet());
    }
}
