package com.ukm.ssgb.dto.board;

import com.ukm.ssgb.model.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminBoardListDto {
    private final long boardId;
    private final String boardTypeName;
    private final String title;
    private final LocalDateTime createdAt;
    private final String nickname;
    private final long likesCount;
    private final long commentsCount;
    private final long viewCount;
    private final boolean deleted;
    private final boolean hidden;

    public AdminBoardListDto(Board board) {
        this.boardId = board.getBoardId();
        this.boardTypeName = board.getBoardType().getDisplayName();
        this.title = board.getTitle();
        this.createdAt = board.getCreatedAt();
        this.nickname = board.getNickname();
        this.likesCount = board.getLikeCount();
        this.commentsCount = board.getComments().size();
        this.viewCount = board.getViewCount();
        this.deleted = board.isDeleted();
        this.hidden = board.isHidden();
    }
}
