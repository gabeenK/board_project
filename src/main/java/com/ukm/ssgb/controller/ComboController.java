package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.combo.BusinessTypesDto;
import com.ukm.ssgb.dto.combo.CategoriesDto;
import com.ukm.ssgb.dto.combo.RegionsDto;
import com.ukm.ssgb.service.BusinessTypeService;
import com.ukm.ssgb.service.CategoryService;
import com.ukm.ssgb.service.RegionService;
import com.ukm.ssgb.type.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/combo")
public class ComboController {

    private final RegionService regionService;
    private final BusinessTypeService businessTypeService;
    private final CategoryService categoryService;

    @GetMapping("/region")
    public ResponseEntity<RegionsDto> getRegions() {
        RegionsDto result = regionService.getRegions();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/businessType")
    public ResponseEntity<BusinessTypesDto> getBusinessTypes() {
        BusinessTypesDto result = businessTypeService.getBusinessTypes();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category")
    public ResponseEntity<CategoriesDto> getCategories(@RequestParam(value = "categoryType") Optional<CategoryType> categoryType) {
        return categoryType.map(type -> ResponseEntity.ok(categoryService.getCategoriesByType(type)))
                .orElseGet(() -> ResponseEntity.ok(categoryService.getCategories()));
    }
}
