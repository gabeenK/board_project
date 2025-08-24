package com.ukm.ssgb.dto.myPage;

import com.ukm.ssgb.type.BoardType;
import com.ukm.ssgb.type.ContentType;
import com.ukm.ssgb.type.DeletedType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyBoardListDto {
    private final long boardId;
    private final ContentType contentType;
    private final String boardTypeName;
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final boolean deleted;
    private final DeletedType deletedType;

    @Builder
    public MyBoardListDto(long boardId, ContentType contentType, BoardType boardType, String title, String content, String nickname, LocalDateTime createdAt, DeletedType deletedType) {
        this.boardId = boardId;
        this.contentType = contentType;
        this.boardTypeName = boardType.getDisplayName();
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.deleted = deletedType.isDeleted();
        this.deletedType = deletedType;
        this.title = deletedType.getBoardTitleByDeletedType(title);
        this.content = getContentByContentType();
    }

    private String getContentByContentType() {
        if (contentType == ContentType.BOARD) {
            return deletedType.getBoardContentByDeletedType(content);
        }
        if (contentType == ContentType.COMMENT) {
            return deletedType.getCommentContentByDeletedType(content);
        }
        throw new RuntimeException();
    }
}
