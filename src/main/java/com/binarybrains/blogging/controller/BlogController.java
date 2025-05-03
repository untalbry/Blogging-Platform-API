package com.binarybrains.blogging.controller;

import com.binarybrains.blogging.dto.BlogRequestDto;
import com.binarybrains.blogging.dto.BlogResponseDto;
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
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody @Valid BlogRequestDto blogCreteDto) {
        return blogService.registerBlog(blogCreteDto.toEntity(), blogCreteDto.getIdCategory())
                .map(blog -> ResponseEntity.ok(BlogResponseDto.fromEntity(blog)))
                .getOrElseGet(error -> {
                    throw new BlogException(error);
                });
    }
    @GetMapping("/{id}")
    public ResponseEntity<BlogResponseDto> readBlog(@PathVariable("id") Long id){
        return blogService.getBlogById(id)
                .map(blog -> ResponseEntity.ok(BlogResponseDto.fromEntity(blog)))
                .getOrElseGet(errorInfo -> {
                    throw new BlogException(errorInfo);
                });
    }
    @PutMapping
    public ResponseEntity<BlogResponseDto> updateBlog(@RequestBody BlogRequestDto blogUpdateDto){
        return blogService.modify(blogUpdateDto.toEntity(), blogUpdateDto.getIdCategory())
                .map(blog -> ResponseEntity.ok(BlogResponseDto.fromEntity(blog)))
                .getOrElseGet(errorInfo -> {
                    throw new BlogException(errorInfo);
                });
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBlog(@PathVariable Long id){
        return blogService.removeBlog(id).map(ResponseEntity::ok)
                .getOrElseGet(errorInfo -> {
                    throw new BlogException(errorInfo);
                });
    }
}
