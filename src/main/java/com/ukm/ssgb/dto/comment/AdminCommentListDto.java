package com.ukm.ssgb.dto.comment;

import com.ukm.ssgb.model.Board;
import com.ukm.ssgb.model.Comment;
import com.ukm.ssgb.type.BoardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AdminCommentListDto {

    private final long commentId;
    private final Long parentCommentId;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final String content;
    private final boolean deleted;
    private final boolean hidden;
    private final long claimCount;
    private final String deletedBy;
    @Setter
    private List<AdminCommentListDto> comments;
    private final CommentListBoardDto board;

    public AdminCommentListDto(Comment comment, Board board) {
        this.commentId = comment.getCommentId();
        this.parentCommentId = comment.getParentCommentId();
        this.nickname = comment.getNickname();
        this.createdAt = comment.getCreatedAt();
        this.content = comment.getContent();
        this.deleted = comment.getDeletedType().isDeleted();
        this.hidden = comment.isHidden();
        this.claimCount = comment.getClaimCount();
        this.deletedBy = comment.getDeletedType().getDisplayName();
        this.comments = new ArrayList<>();
        this.board = new CommentListBoardDto(board);
    }

    @Getter
    private static class CommentListBoardDto {
        private final Long boardId;
        private final BoardType boardType;
        private final String boardTypeName;
        private final String title;

        public CommentListBoardDto(Board board) {
            this.boardId = board.getBoardId();
            this.boardType = board.getBoardType();
            this.boardTypeName = board.getBoardType().getDisplayName();
            this.title = board.getTitle();
        }
    }
}
