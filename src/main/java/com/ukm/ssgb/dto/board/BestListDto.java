package com.ukm.ssgb.dto.board;

import com.ukm.ssgb.model.Board;
import com.ukm.ssgb.util.BoardBestUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BestListDto {
    private long boardId;
    @Setter
    private long rank;
    private String title;
    private String boardTypeName;
    private long likesCount;
    private long viewCount;
    private long commentsCount;
    private long claimCount;
    private LocalDateTime createdAt;
    private String nickname;
    private boolean hidden;
    private boolean pin;
    private double score;

    public static BestListDto toDto(Board board) {
        return BestListDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .boardTypeName(board.getBoardType().getDisplayName())
                .likesCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .commentsCount(board.getComments().size())
                .claimCount(board.getClaimCount())
                .createdAt(board.getCreatedAt())
                .nickname(board.getNickname())
                .hidden(board.isHidden())
                .pin(board.getPin())
                .score(BoardBestUtil.calculateScore(board))
                .build();
    }
}
