package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.BoardBookmarkCode;
import com.ukm.ssgb.model.BoardBookmark;
import com.ukm.ssgb.repository.BoardBookmarkRepository;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardBookmarkFiller {

    private final BoardBookmarkRepository boardBookmarkRepository;

    public void fill(BoardBookmarkCode dto) {
        if (dto == null || Objects.isNull(TokenUtil.getIdWithNull())) {
            return;
        }

        Optional<BoardBookmark> bookmark = boardBookmarkRepository.findByBoardIdAndUserId(dto.getBoardId(), TokenUtil.getIdWithThrow());
        dto.setBookmark(bookmark.isPresent());
    }
}