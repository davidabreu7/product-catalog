package com.devlab.prodcatalog.backend.service;

import com.devlab.prodcatalog.backend.ProductFactory;
import com.devlab.prodcatalog.backend.dto.ProductDto;
import com.devlab.prodcatalog.backend.entities.Product;
import com.devlab.prodcatalog.backend.exceptions.ResourceNotFoundException;
import com.devlab.prodcatalog.backend.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    Long validId;
    Long nonValidId;
    ProductDto productDto;
    Product product;

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository repository;

    @BeforeEach
    public void beforeEach() throws Exception {
        validId = 1L;
        nonValidId = 1000L;
        product = ProductFactory.createProduct();
        productDto = ProductFactory.createProductDto();

        Mockito.when(repository.findById(validId))
                .thenReturn(Optional.of(product))
                .thenAnswer(x -> new ProductDto(product));

        Mockito.when(repository.findById(nonValidId))
                .thenReturn(Optional.empty());
    }

    @Test
    public void findByIdShouldReturnProductDtoWhenIdIsValid() {
        Assertions.assertInstanceOf(ProductDto.class, productService.findById(validId));

    }

    @Test
    public void findByIdShouldThrowExceptionIfProductIdIsNotValid()  {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.findById(nonValidId));
    }


}
