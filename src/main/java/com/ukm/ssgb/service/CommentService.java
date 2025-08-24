package com.ukm.ssgb.service;

import com.ukm.ssgb.dto.TokenDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.Comment;
import com.ukm.ssgb.model.CommentClaim;
import com.ukm.ssgb.model.CommentLike;
import com.ukm.ssgb.repository.BoardRepository;
import com.ukm.ssgb.repository.CommentClaimRepository;
import com.ukm.ssgb.repository.CommentLikeRepository;
import com.ukm.ssgb.repository.CommentRepository;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.ukm.ssgb.type.FolderType.COMMENT;
import static com.ukm.ssgb.util.TokenUtil.getIdWithThrow;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentClaimRepository commentClaimRepository;

    private final FileService fileService;

    @Transactional
    public Comment createComment(Long boardId, String content, List<MultipartFile> attachments, TokenDto userInfo) {
        Comment comment = Comment.builder()
                .board(boardRepository.findById(boardId).orElseThrow())
                .content(content)
                .userId(userInfo.getUserId())
                .nickname(userInfo.getNickname())
                .profileImageId(userInfo.getProfileImageId())
                .fileEntities(fileService.saveAll(COMMENT, attachments))
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment createChildComment(Long boardId,
                                      Long parentCommentId,
                                      String content,
                                      List<MultipartFile> attachments,
                                      TokenDto userInfo) {
        Comment comment = Comment.builder()
                .board(boardRepository.findById(boardId).orElseThrow())
                .parentCommentId(parentCommentId)
                .content(content)
                .userId(userInfo.getUserId())
                .nickname(userInfo.getNickname())
                .profileImageId(userInfo.getProfileImageId())
                .fileEntities(fileService.saveAll(COMMENT, attachments))
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public void claim(Long commentId) {
        Optional<CommentClaim> commentClaim = commentClaimRepository.findByCommentIdAndUserId(commentId, getIdWithThrow());
        if (commentClaim.isPresent()) {
            throw new ValidationException("이미 신고한 댓글입니다.");
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.claim(new CommentClaim(comment, getIdWithThrow()));
    }

    @Transactional
    public void like(Long commentId) {
        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, getIdWithThrow());
        if (commentLike.isPresent()) {
            return;
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.like(new CommentLike(comment, getIdWithThrow()));
    }

    @Transactional
    public void dislike(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.dislike(getIdWithThrow());
    }

    @Transactional
    public Comment updateComment(long userId, Long commentId, String content, List<MultipartFile> attachments,
                                 List<Long> deleteFileIds) {
        Comment comment = commentRepository.findByCommentIdAndUserId(commentId, userId).orElseThrow();
        comment.setContent(content);
        comment.deleteFiles(deleteFileIds);
        comment.updateFiles(fileService.saveAll(COMMENT, attachments));
        return comment;
    }

    @Transactional
    public void deleteCommentByUser(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUserId().equals(TokenUtil.getIdWithThrow())) {
            throw new ValidationException("작성자만 삭제 가능합니다.");
        }
        comment.deleteByUser();
    }

    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.deleteByAdmin();
    }

    @Transactional
    public void deleteCommentsByAdmin(List<Long> commentIds) {
        List<Comment> comments = commentRepository.findAllById(commentIds);
        comments.forEach(Comment::deleteByAdmin);
    }
}
