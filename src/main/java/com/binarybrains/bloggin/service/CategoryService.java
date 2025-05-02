package com.binarybrains.bloggin.service;

import com.binarybrains.bloggin.util.error.ErrorInfo;
import com.binarybrains.bloggin.util.error.ErrorInfoGlobalMapper;
import com.binarybrains.bloggin.model.Category;
import com.binarybrains.bloggin.repository.CategoryRepository;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ErrorInfoGlobalMapper errorMapper;
    public CategoryService(CategoryRepository categoryRepository, ErrorInfoGlobalMapper errorMapper){
        this.categoryRepository = categoryRepository;
        this.errorMapper = errorMapper;
    }

    public Either<ErrorInfo, Category> registerCategory(Category category) {
        return Optional.of(categoryRepository.save(category))
                .map(Either::<ErrorInfo, Category>right)
                .orElseGet(() -> Either.left(errorMapper.getRn001g()));
    }
    public Either<ErrorInfo, Category> getCategory(Long id){
        return Optional.of(categoryRepository.getReferenceById(id))
                .map(Either::<ErrorInfo,Category>right)
                .orElseGet(() -> Either.left(errorMapper.getRn001()));
    }
    public boolean removeCategory(Long id){
        var categoryResult = categoryRepository.findById(id);
        if(categoryResult.isPresent()){
            categoryRepository.delete(categoryResult.get());
            return true;
        }
        return false;
    }
}
