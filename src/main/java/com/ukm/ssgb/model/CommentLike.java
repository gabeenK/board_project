package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "comment_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseCreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long commentLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    @Column
    private Long userId;

    public CommentLike(Comment comment, Long userId) {
        this.comment = comment;
        this.userId = userId;
    }
}