package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "business_type")
public class BusinessType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long businessTypeId;

    @Column
    private String businessTypeName;
}