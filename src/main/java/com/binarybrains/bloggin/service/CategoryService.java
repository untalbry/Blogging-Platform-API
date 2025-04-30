package com.binarybrains.bloggin.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.binarybrains.bloggin.model.Category;
import com.binarybrains.bloggin.repository.CategoryRepository;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Either<Category, Boolean> createCategory(Category category) {
        Either<Category, Boolean> result = Either.right(false);
        var categoryResult = categoryRepository.save(category);
        if (categoryResult != null) {
            result = Either.left(categoryResult);
        }
        return result;
    }
    public Either<Category, Boolean> getCategory(Long id){
        Either<Category, Boolean> result = Either.right(false);
        var categoryResult = categoryRepository.getReferenceById(id);
        if(categoryResult != null ){
            result = Either.left(categoryResult);
        }
        return result;
    }
}
