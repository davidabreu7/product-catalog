package com.devlab.prodcatalog.backend.repositories;

import com.devlab.prodcatalog.backend.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    Long validId;
    Long invalidId;
    @Autowired
    ProductRepository productRepository;


    @BeforeEach
    public void beforeEach()  {
        validId = 1L;
        invalidId = 1000L;
    }

    @Test
    public void findByIdShouldReturnOptionalWhenIdExists(){
        Optional<Product> productOptional = productRepository.findById(validId);
        Assertions.assertNotNull(productOptional);
    }
}
