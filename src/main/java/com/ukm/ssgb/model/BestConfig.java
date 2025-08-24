package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "best_config")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BestConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long bestConfigId;

    @Column
    private Integer bestSize;

    @Column
    private boolean active;

    public void active() {
        this.active = true;
    }
}