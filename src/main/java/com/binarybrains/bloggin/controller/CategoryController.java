package com.binarybrains.bloggin.controller;

import com.binarybrains.bloggin.dto.CategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.binarybrains.bloggin.service.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @PostMapping("/")
    public ResponseEntity<?> createCategory(CategoryDto categoryDto) {
        return categoryService.registerCategory(categoryDto.toEntity())
                .fold(
                        category -> ResponseEntity.ok(CategoryDto.fromEntity(category)),
                        error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la categoría")
                );
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> readCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id).fold(
                category -> ResponseEntity.ok(CategoryDto.fromEntity(category)),
                error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener")
        );
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory( @PathVariable("id") Long id){
        return categoryService.removeCategory(id)
                ? ResponseEntity.ok().body("Eliminación exitosa")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria no encontrada");
    }
}
