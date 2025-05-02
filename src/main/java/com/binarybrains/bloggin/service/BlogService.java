package com.binarybrains.bloggin.service;

import com.binarybrains.bloggin.model.Blog;
import com.binarybrains.bloggin.repository.BlogRepository;
import com.binarybrains.bloggin.repository.CategoryRepository;
import com.binarybrains.bloggin.util.error.ErrorInfo;
import com.binarybrains.bloggin.util.error.ErrorInfoGlobalMapper;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
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

    @Transactional(rollbackOn = Exception.class)
    public Either<ErrorInfo, Blog> registerBlog(Blog blog, Long idCategory){
        return categoryRepository.findById(idCategory)
                .map(category -> {
                    blog.setCategory(category);
                    var blogResult = blogRepository.save(blog);
                    return Either.<ErrorInfo, Blog>right(blogResult);
                })
                .orElseGet(() -> Either.left(errorMapper.getRn001()));
    }
    @Transactional(rollbackOn = Exception.class)
    public Either<ErrorInfo, Blog> getBlogById(Long id){
        return Optional.of(blogRepository.getReferenceById(id))
                .map(Either::<ErrorInfo,Blog>right)
                .orElseGet(() -> Either.left(errorMapper.getRn003()));
    }
}
