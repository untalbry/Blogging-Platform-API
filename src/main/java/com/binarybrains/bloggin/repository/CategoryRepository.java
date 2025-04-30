package com.binarybrains.bloggin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.binarybrains.bloggin.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public  interface CategoryRepository extends JpaRepository<Category, Long> {
}
