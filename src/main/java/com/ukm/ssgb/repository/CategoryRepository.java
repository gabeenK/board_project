package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.Category;
import com.ukm.ssgb.type.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryType(CategoryType categoryType);
}