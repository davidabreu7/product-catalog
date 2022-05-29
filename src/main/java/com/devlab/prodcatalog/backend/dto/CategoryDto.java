package com.devlab.prodcatalog.backend.dto;

import com.devlab.prodcatalog.backend.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDto {

    private Long Id;
    private String name;

    public CategoryDto() {
    }

    public CategoryDto(Long Id, String name) {
        this.Id = Id;
        this.name = name;
    }

    public CategoryDto(Category service) {
        this.Id = service.getId();
        this.name = service.getName();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}
