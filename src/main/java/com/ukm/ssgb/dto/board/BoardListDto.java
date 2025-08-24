package com.ukm.ssgb.dto.board;

import com.ukm.ssgb.decorator.code.BoardBookmarkCode;
import com.ukm.ssgb.decorator.code.BoardLikeCode;
import com.ukm.ssgb.dto.user.AuthorDto;
import com.ukm.ssgb.model.Board;
import com.ukm.ssgb.model.BoardBookmark;
import com.ukm.ssgb.util.TextUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.ukm.ssgb.constant.ServiceConstants.CONTENT_MAX_LENGTH;

@Getter
@Builder
public class BoardListDto implements BoardLikeCode, BoardBookmarkCode {
    private long boardId;
    private String boardTypeName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private AuthorDto author;
    private long likesCount;
    @Setter
    private boolean like;
    private long commentsCount;
    private long viewCount;
    @Setter
    private boolean bookmark;

    public static BoardListDto toDto(Board board) {
        return BoardListDto.builder()
                .boardId(board.getBoardId())
                .boardTypeName(board.getBoardType().getDisplayName())
                .title(board.getTitleForDisplay())
                .content(TextUtil.cutByMaxLength(board.getContentForDisplay(), CONTENT_MAX_LENGTH))
                .createdAt(board.getCreatedAt())
                .author(AuthorDto.of(board.getNickname(), board.getProfileImageId(), board.getUserId()))
                .likesCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .commentsCount(board.getComments().size())
                .build();
    }

    public static BoardListDto toDto(BoardBookmark boardBookmark) {
        return toDto(boardBookmark.getBoard());
    }
}
