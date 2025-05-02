package com.binarybrains.bloggin.service;

import com.binarybrains.bloggin.model.Blog;
import com.binarybrains.bloggin.model.Category;
import com.binarybrains.bloggin.repository.BlogRepository;
import com.binarybrains.bloggin.repository.CategoryRepository;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;

    public BlogService(CategoryRepository categoryRepository, BlogRepository blogRepository){
        this.categoryRepository = categoryRepository;
        this.blogRepository = blogRepository;
    }

    public Either<Blog, Boolean> registerBlog(Blog blog, Long idCategory){
        Either<Blog, Boolean> result = Either.right(false);
        Optional<Category> category = categoryRepository.findById(idCategory);
        if(category.isPresent()){
            blog.setCategory(category.get());
            var blogResult = blogRepository.save(blog);
            if(blogResult != null){
                result = Either.left(blogResult);
            }
        }
        return result;
    }
}
