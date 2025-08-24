package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "board_claim")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardClaim extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long boardClaimId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @Column
    private Long userId;

    public BoardClaim(Board board, Long userId) {
        this.board = board;
        this.userId = userId;
    }
}
