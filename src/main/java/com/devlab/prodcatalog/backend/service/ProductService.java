package com.devlab.prodcatalog.backend.service;

import com.devlab.prodcatalog.backend.dto.ProductDto;
import com.devlab.prodcatalog.backend.entities.Category;
import com.devlab.prodcatalog.backend.entities.Product;
import com.devlab.prodcatalog.backend.exceptions.DatabaseIntegrityException;
import com.devlab.prodcatalog.backend.exceptions.ResourceNotFoundException;
import com.devlab.prodcatalog.backend.repositories.CategoryRepository;
import com.devlab.prodcatalog.backend.repositories.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository CategoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.CategoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(Pageable pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductDto::new);
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(x -> new ProductDto(x, x.getCategories()))
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(id)));
    }

    @Transactional
    public ProductDto insert(ProductDto productDto) {
        Product product = new Product();
        DtoToEntity(productDto, product);
        return new ProductDto(productRepository.save(product), product.getCategories());
    }

    @Transactional
    public ProductDto update(Long id, ProductDto productDto) {
        Product product = productRepository.getById(id);
        DtoToEntity(productDto, product);
        return new ProductDto(productRepository.save(product), product.getCategories());
    }
    @Transactional
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (
                EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Category id: %d not found".formatted(id));
        } catch (
                DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Database Error");
        }
    }

    private void DtoToEntity(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImgUrl(productDto.getImgUrl());

        productDto.getCategories().forEach(cat -> {
            Category category = CategoryRepository.getById(cat.getId());
            product.getCategories().add(category);
        });
    }


}
