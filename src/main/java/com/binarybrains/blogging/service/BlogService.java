package com.binarybrains.blogging.service;

import com.binarybrains.blogging.model.Blog;
import com.binarybrains.blogging.repository.BlogRepository;
import com.binarybrains.blogging.repository.CategoryRepository;
import com.binarybrains.blogging.util.error.ErrorInfo;
import com.binarybrains.blogging.util.error.ErrorInfoGlobalMapper;
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
    @Transactional(rollbackOn = Exception.class)
    public Either<ErrorInfo, Blog> modify(Blog blog, Long idCategory){
        Either<ErrorInfo, Blog> result = Either.left(errorMapper.getRn003());
        var blogResult = blogRepository.findById(blog.getId());
        if (blogResult.isPresent()) {
            var category = categoryRepository.findById(idCategory);
            if (category.isPresent()) {
                Blog existing = blogResult.get();
                existing.setTitle(blog.getTitle());
                existing.setContent(blog.getContent());
                existing.setCategory(category.get());
                Blog blogUpdate = blogRepository.saveAndFlush(existing);
                result = Either.right(blogUpdate);
            }
        }

        return result;
    }
    @Transactional(rollbackOn = Exception.class)
    public Either<ErrorInfo, Boolean> removeBlog(Long id){
        Either<ErrorInfo, Boolean> result = Either.left(errorMapper.getRn003());
        var blogResult = blogRepository.findById(id);
        if(blogResult.isPresent()){
            blogRepository.delete(blogResult.get());
            result = Either.right(true);
        }
        return result;
    }
}
