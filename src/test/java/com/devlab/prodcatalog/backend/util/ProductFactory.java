package com.devlab.prodcatalog.backend.util;

import com.devlab.prodcatalog.backend.dto.ProductDto;
import com.devlab.prodcatalog.backend.entities.Category;
import com.devlab.prodcatalog.backend.entities.Product;

import java.time.Instant;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new Product("Red Death", "Edgar Allan Poe", 20.0, "red_death.png", Instant.parse("2022-06-01T15:00:00Z"));
        product.getCategories().add(new Category(2L, "Books"));
        return product;
    }

    public static ProductDto createProductDto() {
        Product product = createProduct();
        return new ProductDto(product);
    }

}
