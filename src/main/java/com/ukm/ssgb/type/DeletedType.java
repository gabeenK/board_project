package com.ukm.ssgb.type;

import io.jsonwebtoken.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.ukm.ssgb.constant.ServiceConstants.*;

@Getter
@AllArgsConstructor
public enum DeletedType {
    BY_USER(DELETED_BOARD_TITLE, DELETED_BOARD_CONTENT, DELETED_COMMENT_CONTENT, DELETED_BY_USER_DISPLAY_NAME),
    BY_ADMIN(DELETED_BOARD_TITLE, DELETED_BOARD_CONTENT_BY_ADMIN, DELETED_COMMENT_CONTENT_BY_ADMIN, DELETED_BY_ADMIN_DISPLAY_NAME),
    NONE(null, null, null, "");

    private final String deletedBoardTitleMessage;
    private final String deletedBoardContentMessage;
    private final String deletedCommentContentMessage;
    private final String displayName;

    public boolean isNotDeleted() {
        return this == NONE;
    }

    public boolean isDeleted() {
        return this != NONE;
    }

    public String getBoardTitleByDeletedType(String title) {
        return getTextByType(title, deletedBoardTitleMessage);
    }

    public String getBoardContentByDeletedType(String content) {
        return getTextByType(content, deletedBoardContentMessage);
    }

    public String getCommentContentByDeletedType(String content) {
        return getTextByType(content, deletedCommentContentMessage);
    }

    private String getTextByType(String text, String deletedMessage) {
        if (text == null) {
            return Strings.EMPTY;
        }
        return isNotDeleted() ? text : deletedMessage;
    }
}
