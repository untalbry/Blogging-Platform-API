package com.binarybrains.bloggin.controller;

import com.binarybrains.bloggin.dto.CategoryDto;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.binarybrains.bloggin.service.CategoryService;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @PostMapping("/")
    public ResponseEntity<?> createCategory(CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto.toEntity())
                .fold(
                        category -> ResponseEntity.ok(CategoryDto.fromEntity(category)),
                        error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la categoría")
                );
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id).fold(
                category -> ResponseEntity.ok(CategoryDto.fromEntity(category)),
                error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener")
        );
    }
}
