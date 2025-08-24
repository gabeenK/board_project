package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long regionId;

    @Column
    private String regionName;
}