package com.devlab.prodcatalog.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryResource {


    @GetMapping
    public String findAll(){
        return "Categorias";
    }

}
