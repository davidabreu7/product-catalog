package com.devlab.prodcatalog.backend.dto;

import com.devlab.prodcatalog.backend.entities.Category;
import com.devlab.prodcatalog.backend.entities.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDto {

    private Long id;
    @Size(min = 5, max = 50, message = "Nome deve ter entre 5 e 50 caracteres")
    @NotBlank(message = "Campo obrigatório")
    private String name;
    @Positive(message = "Preço deve ser positivo")
    private Double price;
    @NotBlank
    private String description;

    private String imgUrl;

    private List<CategoryDto> categories = new ArrayList<>();

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imgUrl = product.getImgUrl();
    }

    public ProductDto(Long id, String name, Double price, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
    }


    public ProductDto(Product product, Set<Category> categories) {
        this(product);
        categories.forEach(cat -> this.categories.add(new CategoryDto(cat)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                '}';
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
