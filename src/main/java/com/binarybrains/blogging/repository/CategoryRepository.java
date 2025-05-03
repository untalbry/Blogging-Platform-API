package com.binarybrains.blogging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.binarybrains.blogging.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public  interface CategoryRepository extends JpaRepository<Category, Long> {
}
