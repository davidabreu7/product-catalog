package com.devlab.prodcatalog.backend.service;

import com.devlab.prodcatalog.backend.dto.UserDto;
import com.devlab.prodcatalog.backend.entities.User;
import com.devlab.prodcatalog.backend.exceptions.DatabaseIntegrityException;
import com.devlab.prodcatalog.backend.exceptions.ResourceNotFoundException;
import com.devlab.prodcatalog.backend.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public UserDto insert(UserDto userDto) {
        return new UserDto(userRepository.save(new User(userDto)));
    }

    public UserDto updateById(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(id)));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        return new UserDto(user);
    }
}
