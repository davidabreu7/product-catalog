package com.devlab.prodcatalog.backend.controller;

import com.devlab.prodcatalog.backend.dto.ProductDto;
import com.devlab.prodcatalog.backend.service.ProductService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Cacheable(value = "allProducts")
    public ResponseEntity<Page<ProductDto>> findAll(@PageableDefault(value = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "productById")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    @CacheEvict(value = {"allProducts", "productById"}, allEntries = true)
    public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.insert(productDto));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = {"allProducts", "productById"}, allEntries = true)
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.update(id, productDto));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"allProducts", "productById"}, allEntries = true)
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

}
