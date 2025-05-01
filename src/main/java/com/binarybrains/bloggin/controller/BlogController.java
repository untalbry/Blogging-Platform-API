package com.binarybrains.bloggin.controller;

import com.binarybrains.bloggin.dto.BlogDto;
import com.binarybrains.bloggin.model.Blog;
import com.binarybrains.bloggin.service.BlogService;
import io.vavr.control.Either;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }
    @PostMapping("/")
    public ResponseEntity<BlogDto> createBlog(@RequestBody  BlogDto blogDto){
        return blogService.registerBlog(blogDto.toEntity(), blogDto.getIdCategory()).fold(
                blog -> ResponseEntity.ok(BlogDto.fromEntity(blog)),
                error -> {throw new RuntimeException("Error al crear un blog");
                });
    }
}
