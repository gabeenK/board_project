package com.ukm.ssgb.dto.board;

import com.ukm.ssgb.decorator.code.BoardBookmarkCode;
import com.ukm.ssgb.decorator.code.BoardClaimCode;
import com.ukm.ssgb.decorator.code.BoardLikeCode;
import com.ukm.ssgb.dto.AttachmentDto;
import com.ukm.ssgb.dto.comment.CommentDetailDto;
import com.ukm.ssgb.dto.user.AuthorDto;
import com.ukm.ssgb.model.Board;
import com.ukm.ssgb.type.BoardType;
import com.ukm.ssgb.util.TextUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ukm.ssgb.constant.ServiceConstants.CONTENT_MAX_LENGTH;

@Getter
@Builder
public class BoardDetailDto implements BoardLikeCode, BoardBookmarkCode, BoardClaimCode {
    private long boardId;
    private BoardType boardType;
    private String boardTypeName;
    private AuthorDto author;
    private LocalDateTime createdAt;
    private String title;
    private String content;
    private List<AttachmentDto> attachments;
    private long likesCount;
    @Setter
    private boolean like;
    private long commentsCount;
    private long viewCount;
    @Setter
    private boolean bookmark;
    private boolean deleted;
    @Setter
    private boolean claim;
    @Setter
    private List<CommentDetailDto> comments;

    public static BoardDetailDto toDto(Board board) {
        return BoardDetailDto.builder()
                .boardId(board.getBoardId())
                .boardType(board.getBoardType())
                .boardTypeName(board.getBoardType().getDisplayName())
                .author(AuthorDto.of(board.getNickname(), board.getProfileImageId(), board.getUserId()))
                .createdAt(board.getCreatedAt())
                .title(board.getTitleForDisplay())
                .content(TextUtil.cutByMaxLength(board.getContentForDisplay(), CONTENT_MAX_LENGTH))
                .attachments(board.getAttachmentsForDisplay())
                .likesCount(board.getLikeCount())
                .commentsCount(board.getComments().size())
                .viewCount(board.getViewCount())
                .deleted(board.isDeleted())
                .comments(new ArrayList<>())
                .build();
    }
}
