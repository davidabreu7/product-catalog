package com.devlab.prodcatalog.backend.service;

import com.devlab.prodcatalog.backend.dto.CategoryDto;
import com.devlab.prodcatalog.backend.entities.Category;
import com.devlab.prodcatalog.backend.exceptions.DatabaseIntegrityException;
import com.devlab.prodcatalog.backend.exceptions.ResourceNotFoundException;
import com.devlab.prodcatalog.backend.repositories.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> findAll(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest).map(CategoryDto::new);
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long Id) {
        Optional<Category> category = categoryRepository.findById(Id);
        return category.map(CategoryDto::new).orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Id %d not found", Id)));
    }

    @Transactional
    public CategoryDto insert(CategoryDto dto) {
        Category category = new Category(dto);
        return new CategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(id)));
        category.setName(dto.getName());
        categoryRepository.save(category);
        return new CategoryDto(category);
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Category id: %d not found".formatted(id));
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Database Error");
        }

    }
}
