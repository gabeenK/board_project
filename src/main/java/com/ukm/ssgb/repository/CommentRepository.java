package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {
    Optional<Comment> findByCommentIdAndUserId(Long commentId, Long userId);
}