package com.binarybrains.bloggin.controller;

import com.binarybrains.bloggin.dto.BlogDto;
import com.binarybrains.bloggin.service.BlogService;
import com.binarybrains.bloggin.util.error.BlogException;
import com.binarybrains.bloggin.util.error.ErrorInfoGlobalMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final ErrorInfoGlobalMapper errorMapper;
    public BlogController(BlogService blogService, ErrorInfoGlobalMapper errorMapper){
        this.errorMapper = errorMapper;
        this.blogService = blogService;
    }
    @PostMapping("/")
    public ResponseEntity<BlogDto> createBlog(@RequestBody  BlogDto blogDto){
        return blogService.registerBlog(blogDto.toEntity(), blogDto.getIdCategory()).fold(
                blog -> ResponseEntity.ok(BlogDto.fromEntity(blog)),
                error -> {throw new BlogException(errorMapper.getRn001());
                });
    }
}
