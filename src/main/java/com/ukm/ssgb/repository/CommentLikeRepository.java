package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("select b from CommentLike b where b.comment.commentId = ?1 and b.userId = ?2")
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
}
