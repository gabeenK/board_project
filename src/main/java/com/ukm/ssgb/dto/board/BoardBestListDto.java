package com.ukm.ssgb.dto.board;

import com.ukm.ssgb.dto.user.AuthorDto;
import com.ukm.ssgb.model.Board;
import com.ukm.ssgb.util.BoardBestUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardBestListDto {
    private long boardId;
    private String boardTypeName;
    private String title;
    private LocalDateTime createdAt;
    private AuthorDto author;
    private boolean pin;
    private double score;

    public static BoardBestListDto toDto(Board board) {
        return BoardBestListDto.builder()
                .boardId(board.getBoardId())
                .boardTypeName(board.getBoardType().getDisplayName())
                .title(board.getTitleForDisplay())
                .createdAt(board.getCreatedAt())
                .author(AuthorDto.of(board.getNickname(), board.getProfileImageId(), board.getUserId()))
                .pin(board.getPin())
                .score(BoardBestUtil.calculateScore(board))
                .build();
    }
}
