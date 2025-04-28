package com.binarybrains.bloggin.controller;

import com.binarybrains.bloggin.dto.CategoryDto;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binarybrains.bloggin.service.CategoryService;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/")
    public ResponseEntity<?> getMethodName(CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto.toEntity())
                .fold(
                        category -> ResponseEntity.ok(CategoryDto.fromEntity(category)),
                        error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la categoría")
                );
    }
}
