package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "nickname")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname extends BaseCreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long nicknameId;

    @Column
    private String adjective;

    @Column
    private String noun;

    @Column
    private Boolean active;

    @Column
    private Long userId;

    @Builder
    public Nickname(String adjective, String noun) {
        this.adjective = adjective;
        this.noun = noun;
        this.active = false;
    }

    public String getNickName() {
        return this.adjective + " " + this.noun;
    }

    public void activeNickname(long userId) {
        this.active = true;
        this.userId = userId;
    }
}