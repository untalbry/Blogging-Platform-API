package com.binarybrains.bloggin.controller;

import com.binarybrains.bloggin.dto.BlogDto;
import com.binarybrains.bloggin.service.BlogService;
import com.binarybrains.bloggin.util.error.BlogException;
import com.binarybrains.bloggin.util.error.ErrorInfoGlobalMapper;
import com.binarybrains.bloggin.util.error.handler.GlobalExceptionHandler;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService, ErrorInfoGlobalMapper errorMapper){
        this.blogService = blogService;
    }
    @PostMapping("/")
    public ResponseEntity<BlogDto> createBlog(@RequestBody BlogDto blogDto) {
        return blogService.registerBlog(blogDto.toEntity(), blogDto.getIdCategory())
                .map(blog -> ResponseEntity.ok(BlogDto.fromEntity(blog)))
                .getOrElseGet(error -> {
                    throw new BlogException(error);
                });
    }
}
