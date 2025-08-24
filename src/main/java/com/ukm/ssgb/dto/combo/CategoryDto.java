package com.ukm.ssgb.dto.combo;

import com.ukm.ssgb.decorator.code.FilePathCode;
import com.ukm.ssgb.model.Category;
import com.ukm.ssgb.type.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CategoryDto implements FilePathCode {

    private final Long categoryId;
    private final CategoryType categoryType;
    private final String categoryTypeName;
    private final String category;
    private final Long fileId;

    @Setter
    private String filePath;

    public static CategoryDto of(Category category) {
        return new CategoryDto(category);
    }

    private CategoryDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryType = category.getCategoryType();
        this.categoryTypeName = category.getCategoryType().getCategoryTypeName();
        this.category = category.getCategory();
        this.fileId = category.getCategoryImageId();
    }
}
