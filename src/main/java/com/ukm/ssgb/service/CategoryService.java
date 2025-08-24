package com.ukm.ssgb.service;

import com.ukm.ssgb.decorator.filler.FilePathFiller;
import com.ukm.ssgb.dto.combo.CategoriesDto;
import com.ukm.ssgb.dto.combo.CategoryDto;
import com.ukm.ssgb.repository.CategoryRepository;
import com.ukm.ssgb.type.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final FilePathFiller filePathFiller;


    @Transactional(readOnly = true)
    public CategoriesDto getCategoriesByType(CategoryType categoryType) {
        List<CategoryDto> categories = categoryRepository.findByCategoryType(categoryType)
                .stream()
                .map(CategoryDto::of)
                .toList();

        categories.forEach(filePathFiller::fill);

        return new CategoriesDto(categories);
    }

    @Transactional(readOnly = true)
    public CategoriesDto getCategories() {
        List<CategoryDto> categories = categoryRepository.findAll()
                .stream()
                .map(CategoryDto::of)
                .toList();

        categories.forEach(filePathFiller::fill);

        return new CategoriesDto(categories);
    }
}
