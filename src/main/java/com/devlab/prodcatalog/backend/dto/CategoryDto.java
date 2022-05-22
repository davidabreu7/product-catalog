package com.devlab.prodcatalog.backend.dto;

import com.devlab.prodcatalog.backend.entities.Category;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDto that = (CategoryDto) o;
        return Id.equals(that.Id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name);
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}
