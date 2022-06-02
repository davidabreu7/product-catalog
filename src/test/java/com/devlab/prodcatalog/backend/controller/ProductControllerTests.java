package com.devlab.prodcatalog.backend.controller;

import com.devlab.prodcatalog.backend.ProductFactory;
import com.devlab.prodcatalog.backend.dto.ProductDto;
import com.devlab.prodcatalog.backend.exceptions.ResourceNotFoundException;
import com.devlab.prodcatalog.backend.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    ObjectMapper mapper;
    ProductDto productDto;
    Long validId;
    Long nonValidId;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setup() throws Exception {
        productDto = ProductFactory.createProductDto();
        validId = 1L;
        nonValidId = 2L;
        Mockito.when(productService.insert(productDto)).thenReturn(productDto);
        Mockito.doNothing().when(productService).delete(validId);
        Mockito.doThrow(ResourceNotFoundException.class).when(productService).delete(nonValidId);
    }

    @Test
    public void insertShouldReturnProductDto() throws Exception {

        String jsonBody = mapper.writeValueAsString(productDto);
        ResultActions result = mockMvc.perform(post("/api/v1/products/")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenNonValidId() throws Exception {
        ResultActions result = mockMvc.perform(delete("/api/v1/products/{id}", nonValidId));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnGoneWhenValidId() throws Exception {
        ResultActions result = mockMvc.perform(delete("/api/v1/products/{id}", validId));
        result.andExpect(status().isGone());
    }

}
