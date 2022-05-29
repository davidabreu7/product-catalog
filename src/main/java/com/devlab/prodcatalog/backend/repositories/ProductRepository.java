package com.devlab.prodcatalog.backend.repositories;

import com.devlab.prodcatalog.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
