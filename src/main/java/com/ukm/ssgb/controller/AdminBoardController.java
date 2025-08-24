package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.board.*;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.dto.page.PageContentsWithBestSizeDto;
import com.ukm.ssgb.dto.page.PageRequestDto;
import com.ukm.ssgb.facade.BoardBestFacade;
import com.ukm.ssgb.facade.BoardFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/board")
public class AdminBoardController {

    private final BoardBestFacade boardBestFacade;
    private final BoardFacade boardFacade;

    @GetMapping(value = "/best")
    public ResponseEntity<PageContentsWithBestSizeDto<List<BestListDto>>> bestList(@Valid PageRequestDto pageRequestDto) {
        PageContentsWithBestSizeDto<List<BestListDto>> result = boardBestFacade.list(pageRequestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/best/config")
    public ResponseEntity<BestConfigsDto> bestConfigList() {
        BestConfigsDto result = boardBestFacade.configList();
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/best/config")
    public ResponseEntity<Void> updateBestConfig(@Valid @RequestBody BestConfigUpdateRequestDto requestDto) {
        boardBestFacade.updateConfig(requestDto.getBestSize());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{boardId}/pin")
    public ResponseEntity<Void> setPin(@PathVariable("boardId") Long boardId) {
        boardFacade.setPin(boardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}/pin")
    public ResponseEntity<Void> removePin(@PathVariable("boardId") Long boardId) {
        boardFacade.removePin(boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageContentsDto<List<AdminBoardListDto>>> getBoards(@Valid AdminBoardsRequestDto requestDto) {
        return ResponseEntity.ok(boardFacade.listByAdmin(requestDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBoards(@Valid @RequestBody DeleteBoardsRequestDto requestDto) {
        boardFacade.deleteBoardsByAdmin(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardIdDto> createBoard(@Valid @ModelAttribute AdminBoardCreateRequestDto requestDto) {
        BoardIdDto result = boardFacade.createByAdmin(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{boardId}")
    public ResponseEntity<BoardDetailDto> getBoard(@PathVariable("boardId") Long boardId) {
        BoardDetailDto result = boardFacade.get(boardId);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{boardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardIdDto> updateBoard(@PathVariable("boardId") Long boardId,
                                                  @Valid @ModelAttribute AdminBoardUpdateRequestDto requestDto) {
        BoardIdDto result = boardFacade.updateBoardByAdmin(boardId, requestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardFacade.deleteBoardByAdmin(boardId);
        return ResponseEntity.ok().build();
    }
}
