package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.board.*;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.dto.page.PageRequestDto;
import com.ukm.ssgb.facade.BoardBestFacade;
import com.ukm.ssgb.facade.BoardFacade;
import com.ukm.ssgb.facade.EventFacade;
import com.ukm.ssgb.type.BoardType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {

    private final BoardFacade boardFacade;
    private final BoardBestFacade boardBestFacade;
    private final EventFacade eventFacade;

    @GetMapping
    public ResponseEntity<PageContentsDto<List<BoardListDto>>> boardList(@RequestParam(value = "boardType") BoardType boardType,
                                                                         @Valid PageRequestDto pageRequestDto) {
        PageContentsDto<List<BoardListDto>> result = boardFacade.list(boardType, pageRequestDto.getPageRequest());
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/best")
    public ResponseEntity<PageContentsDto<List<BoardBestListDto>>> boardBestList() {
        PageContentsDto<List<BoardBestListDto>> result = boardBestFacade.listByUser();
        return ResponseEntity.ok(result);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardIdDto> createBoard(@Valid @ModelAttribute BoardCreateRequestDto requestDto) {
        BoardIdDto result = boardFacade.create(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{boardId}")
    public ResponseEntity<BoardDetailDto> getBoard(@PathVariable("boardId") Long boardId) {
        BoardDetailDto result = boardFacade.get(boardId);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{boardId}")
    public ResponseEntity<BoardIdDto> updateBoard(@PathVariable("boardId") Long boardId,
                                                  @Valid @ModelAttribute BoardUpdateRequestDto requestDto) {
        BoardIdDto result = boardFacade.updateBoard(boardId, requestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardFacade.deleteBoardByUser(boardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{boardId}/claim")
    public ResponseEntity<Void> claim(@PathVariable("boardId") Long boardId) {
        boardFacade.claim(boardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{boardId}/like")
    public ResponseEntity<Void> like(@PathVariable("boardId") Long boardId) {
        boardFacade.like(boardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}/like")
    public ResponseEntity<Void> dislike(@PathVariable("boardId") Long boardId) {
        boardFacade.dislike(boardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{boardId}/bookmark")
    public ResponseEntity<Void> bookmark(@PathVariable("boardId") Long boardId) {
        boardFacade.bookmark(boardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}/bookmark")
    public ResponseEntity<Void> unBookmark(@PathVariable("boardId") Long boardId) {
        boardFacade.unBookmark(boardId);
        return ResponseEntity.ok().build();
    }
}
