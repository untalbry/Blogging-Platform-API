package com.binarybrains.bloggin.service;

import com.binarybrains.bloggin.util.error.ErrorInfo;
import com.binarybrains.bloggin.util.error.ErrorInfoGlobalMapper;
import com.binarybrains.bloggin.model.Category;
import com.binarybrains.bloggin.repository.CategoryRepository;
import io.vavr.control.Either;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ErrorInfoGlobalMapper errorMapper;
    private final EntityManager entityManager;
    private static final String GET_BY_NAME_QUERY = "SELECT * FROM b02_category WHERE tx_name=:categoryName";
    private static final String PARAM_CATEGORY_NAME = "categoryName";
    public CategoryService(CategoryRepository categoryRepository, ErrorInfoGlobalMapper errorMapper, EntityManager entityManager){
        this.categoryRepository = categoryRepository;
        this.errorMapper = errorMapper;
        this.entityManager = entityManager;
    }

    @Transactional(rollbackOn = Exception.class)
    public Either<ErrorInfo, Category> registerCategory(Category category) {
        Either<ErrorInfo, Category> result = Either.left(errorMapper.getRn002());
        var categoryResult = entityManager.createNativeQuery(GET_BY_NAME_QUERY,  Category.class)
                .setParameter(PARAM_CATEGORY_NAME, category.getName()).getResultStream().findFirst();
        if(categoryResult.isEmpty()){
            var categoryCreated = categoryRepository.save(category);
            result = Either.right(categoryCreated);
        }
        return result;
    }

    @Transactional(rollbackOn = Exception.class)
    public Either<ErrorInfo, Category> getCategory(Long id){
        return Optional.of(categoryRepository.getReferenceById(id))
                .map(Either::<ErrorInfo,Category>right)
                .orElseGet(() -> Either.left(errorMapper.getRn001()));
    }

    @Transactional(rollbackOn = Exception.class)
    public boolean removeCategory(Long id){
        var categoryResult = categoryRepository.findById(id);
        if(categoryResult.isPresent()){
            categoryRepository.delete(categoryResult.get());
            return true;
        }
        return false;
    }
    @Transactional(rollbackOn = Exception.class)
    public Either<ErrorInfo, Category> modify(Category category){
        Either<ErrorInfo, Category> result = Either.left(errorMapper.getRn003());
        var categoryResult = categoryRepository.findById(category.getId());
        if(categoryResult.isPresent()){
            var categoryUpdate = categoryRepository.saveAndFlush(category);
            result = Either.right(categoryUpdate);
        }
        return result;
    }
}
