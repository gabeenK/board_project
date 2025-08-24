package com.ukm.ssgb.dto.comment;

import com.ukm.ssgb.decorator.code.CommentClaimCode;
import com.ukm.ssgb.decorator.code.CommentLikeCode;
import com.ukm.ssgb.dto.AttachmentDto;
import com.ukm.ssgb.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommentDetailDto implements CommentLikeCode, CommentClaimCode {
    private Long commentId;
    private Long parentCommentId;
    private String nickname;
    private Long userId;
    private LocalDateTime createdAt;
    private String content;
    @Setter
    private List<AttachmentDto> attachments;
    private long likeCount;
    @Setter
    private boolean like;
    private boolean deleted;
    @Setter
    private boolean claim;
    @Setter
    private List<CommentDetailDto> comments;

    public static CommentDetailDto toDto(Comment comment) {
        return CommentDetailDto.builder()
                .commentId(comment.getCommentId())
                .parentCommentId(comment.getParentCommentId())
                .nickname(comment.getNickname())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .content(comment.getContentForDisplay())
                .attachments(comment.getAttachmentsForDisplay())
                .likeCount(comment.getLikeCount())
                .deleted(comment.isDeleted())
                .comments(new ArrayList<>())
                .build();
    }
}
