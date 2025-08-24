package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ukm.ssgb.constant.ServiceConstants.NICKNAME_MAX_COUNT;

@Getter
@Entity
@Table(name = "nickname_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameCount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long nicknameCountId;

    @Column
    private Long userId;

    @Column
    private Integer count;

    public NicknameCount(Long userId) {
        this.userId = userId;
        this.count = 0;
    }

    public void plusCount() {
        this.count++;
    }

    public boolean overMaxCount() {
        return this.count >= NICKNAME_MAX_COUNT;
    }

    public Integer getRemainingCount() {
        return NICKNAME_MAX_COUNT - this.count;
    }
}