package com.binarybrains.bloggin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @GetMapping("/")
    public String getMethodName() {
        return new String("Hola spring boot");
    }
    
}
