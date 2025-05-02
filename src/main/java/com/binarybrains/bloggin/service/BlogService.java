package com.binarybrains.bloggin.service;

import com.binarybrains.bloggin.model.Blog;
import com.binarybrains.bloggin.model.Category;
import com.binarybrains.bloggin.repository.BlogRepository;
import com.binarybrains.bloggin.repository.CategoryRepository;
import com.binarybrains.bloggin.util.error.ErrorInfo;
import com.binarybrains.bloggin.util.error.ErrorInfoGlobalMapper;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final ErrorInfoGlobalMapper errorMapper;


    public BlogService(CategoryRepository categoryRepository, BlogRepository blogRepository, ErrorInfoGlobalMapper errorMapper){
        this.categoryRepository = categoryRepository;
        this.blogRepository = blogRepository;
        this.errorMapper = errorMapper;

    }

    public Either<ErrorInfo, Blog> registerBlog(Blog blog, Long idCategory){
        return categoryRepository.findById(idCategory)
                .map(category -> {
                    blog.setCategory(category);
                    var blogResult = blogRepository.save(blog);
                    return Either.<ErrorInfo, Blog>right(blogResult);
                })
                .orElseGet(() -> Either.left(errorMapper.getRn001()));
    }
}
