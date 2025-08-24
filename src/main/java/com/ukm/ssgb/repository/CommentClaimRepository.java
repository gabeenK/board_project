package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.CommentClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentClaimRepository extends JpaRepository<CommentClaim, Long> {

    @Query("select b from CommentClaim b where b.comment.commentId = ?1 and b.userId = ?2")
    Optional<CommentClaim> findByCommentIdAndUserId(Long commentId, Long userId);
}
