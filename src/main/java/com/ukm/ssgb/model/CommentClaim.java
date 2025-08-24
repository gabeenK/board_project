package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment_claim")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentClaim extends BaseCreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long commentClaimId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    @Column
    private Long userId;

    public CommentClaim(Comment comment, Long userId) {
        this.comment = comment;
        this.userId = userId;
    }
}