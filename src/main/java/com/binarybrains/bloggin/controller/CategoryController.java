package com.binarybrains.bloggin.controller;

import com.binarybrains.bloggin.dto.CategoryDto;
import com.binarybrains.bloggin.util.error.BlogException;
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
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.registerCategory(categoryDto.toEntity())
                .map(category -> ResponseEntity.ok(CategoryDto.fromEntity(category)))
                .getOrElseGet(errorInfo -> {
                    throw new BlogException(errorInfo);
                });
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> readCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id)
                .map(category -> ResponseEntity.ok(CategoryDto.fromEntity(category)))
                .getOrElseGet(errorInfo -> {
                    throw new BlogException(errorInfo);
                });
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory( @PathVariable("id") Long id){
        return categoryService.removeCategory(id)
                ? ResponseEntity.ok().body("Eliminación exitosa")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria no encontrada");
    }
    @PutMapping("/")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.modify(categoryDto.toEntity())
                .map(category -> ResponseEntity.ok(CategoryDto.fromEntity(category)))
                .getOrElseGet(errorInfo -> {
                    throw new BlogException(errorInfo);
                });
    }
}
