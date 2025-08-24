package com.ukm.ssgb.model;

import com.ukm.ssgb.type.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    @Column
    private CategoryType categoryType;

    @Column
    private String category;

    @Column
    private Long categoryImageId;
}