package com.ukm.ssgb.decorator;

import com.ukm.ssgb.decorator.filler.*;
import com.ukm.ssgb.dto.board.BoardDetailDto;
import com.ukm.ssgb.dto.board.BoardListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardListDecorator {
    private final BoardLikeFiller boardLikeFiller;
    private final BoardBookmarkFiller boardBookmarkFiller;
    private final BoardClaimFiller boardClaimFiller;
    private final FileNameFiller fileNameFiller;
    private final FilePathFiller filePathFiller;

    public void decorate(BoardListDto dto) {
        if (dto == null) {
            return;
        }
        boardLikeFiller.fill(dto);
        boardBookmarkFiller.fill(dto);
        filePathFiller.fill(dto.getAuthor());
    }

    public void decorate(BoardDetailDto dto) {
        if (dto == null) {
            return;
        }
        boardLikeFiller.fill(dto);
        boardBookmarkFiller.fill(dto);
        boardClaimFiller.fill(dto);

        fileNameFiller.fill(dto.getAttachments());
        filePathFiller.fill(dto.getAuthor());
    }
}