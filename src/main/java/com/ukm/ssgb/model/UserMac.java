package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_mac")
public class UserMac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userMacId;

    @Column
    private Long userId;

    @Column
    private String macAddress;
}