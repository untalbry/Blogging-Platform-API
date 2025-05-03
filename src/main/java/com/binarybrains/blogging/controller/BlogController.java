package com.binarybrains.blogging.controller;

import com.binarybrains.blogging.dto.BlogCreateDto;
import com.binarybrains.blogging.dto.BlogDto;
import com.binarybrains.blogging.model.Blog;
import com.binarybrains.blogging.service.BlogService;
import com.binarybrains.blogging.util.error.BlogException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }
    @PostMapping("/")
    public ResponseEntity<BlogDto> createBlog(@RequestBody @Valid BlogCreateDto blogCreteDto) {
        return blogService.registerBlog(blogCreteDto.toEntity(), blogCreteDto.getIdCategory())
                .map(blog -> ResponseEntity.ok(BlogDto.fromEntity(blog)))
                .getOrElseGet(error -> {
                    throw new BlogException(error);
                });
    }
    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> readBlog(@PathVariable("id") Long id){
        return blogService.getBlogById(id)
                .map(blog -> ResponseEntity.ok(BlogDto.fromEntity(blog)))
                .getOrElseGet(errorInfo -> {
                    throw new BlogException(errorInfo);
                });
    }
    @PutMapping
    public ResponseEntity<BlogDto> updateCategory(@RequestBody BlogDto blogDto){
        return blogService.modify(blogDto.toEntity(), blogDto.getIdCategory())
                .map(blog -> ResponseEntity.ok(BlogDto.fromEntity(blog)))
                .getOrElseGet(errorInfo -> {
                    throw new BlogException(errorInfo);
                });
    }


}
