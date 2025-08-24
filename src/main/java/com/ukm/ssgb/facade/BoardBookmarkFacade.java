package com.ukm.ssgb.facade;

import com.ukm.ssgb.decorator.BoardListDecorator;
import com.ukm.ssgb.dto.board.BoardListDto;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.model.BoardBookmark;
import com.ukm.ssgb.repository.BoardBookmarkRepository;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardBookmarkFacade {

    private final BoardBookmarkRepository boardBookmarkRepository;
    private final BoardListDecorator boardListDecorator;

    @Transactional(readOnly = true)
    public PageContentsDto<List<BoardListDto>> list(PageRequest pageRequest) {
        Pageable pageable = pageRequest.withSort(Sort.by("createdAt").descending());
        Page<BoardBookmark> boardBookmarkList = boardBookmarkRepository.findAllByUserId(TokenUtil.getIdWithThrow(), pageable);

        List<BoardListDto> result = boardBookmarkList.stream().map(BoardListDto::toDto).toList();
        result.forEach(boardListDecorator::decorate);

        return PageContentsDto.of(result, boardBookmarkList.getTotalElements());
    }
}
