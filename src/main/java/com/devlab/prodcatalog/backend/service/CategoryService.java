package com.devlab.prodcatalog.backend.service;

import com.devlab.prodcatalog.backend.dto.CategoryDto;
import com.devlab.prodcatalog.backend.entities.Category;
import com.devlab.prodcatalog.backend.exceptions.ResourceNotFoundException;
import com.devlab.prodcatalog.backend.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    public CategoryDto findById(Long Id) {
        Optional<Category> category = categoryRepository.findById(Id);
        return category.map(CategoryDto::new).
                orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Id %d not found", Id)));
    }

}
