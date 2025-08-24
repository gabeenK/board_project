package com.ukm.ssgb.facade;

import com.ukm.ssgb.decorator.BoardListDecorator;
import com.ukm.ssgb.dto.board.*;
import com.ukm.ssgb.dto.comment.CommentDetailDto;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.Board;
import com.ukm.ssgb.repository.BoardRepository;
import com.ukm.ssgb.service.BoardService;
import com.ukm.ssgb.service.FileService;
import com.ukm.ssgb.type.BoardType;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ukm.ssgb.type.FolderType.BOARD;
import static com.ukm.ssgb.util.TokenUtil.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class BoardFacade {

    private static final Integer MAX_PIN_COUNT = 3;

    private final BoardRepository boardRepository;
    private final BoardListDecorator boardListDecorator;
    private final FileService fileService;
    private final BoardService boardService;

    @Transactional(readOnly = true)
    public PageContentsDto<List<BoardListDto>> list(BoardType boardType, PageRequest pageRequest) {
        Pageable pageable = pageRequest.withSort(Sort.by("createdAt").descending());
        Page<Board> boardList = boardRepository.findAllByBoardType(boardType, pageable);

        List<BoardListDto> result = boardList.stream().map(BoardListDto::toDto).toList();
        result.forEach(boardListDecorator::decorate);

        return PageContentsDto.of(result, boardList.getTotalElements());
    }

    @Transactional
    public BoardIdDto create(BoardCreateRequestDto requestDto) {
        Board board = Board.builder()
                .userId(getIdWithThrow())
                .nickname(getNicknameWithThrow())
                .profileImageId(getProfileIdWithNull())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .boardType(requestDto.getBoardType())
                .fileEntities(fileService.saveAll(BOARD, requestDto.getAttachments()))
                .build();

        boardRepository.save(board);

        return new BoardIdDto(board.getBoardId());
    }

    @Transactional
    public BoardDetailDto get(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.view();

        BoardDetailDto boardDetailDto = boardService.toBoardDetailDto(board);
        List<CommentDetailDto> commentDetailDtoList = boardService.organizeComments(board.getComments());
        boardDetailDto.setComments(commentDetailDtoList);

        return boardDetailDto;
    }

    public void claim(Long boardId) {
        boardService.claim(boardId);
    }

    public void like(Long boardId) {
        boardService.like(boardId);
    }

    public void dislike(Long boardId) {
        boardService.dislike(boardId);
    }

    public void bookmark(Long boardId) {
        boardService.bookmark(boardId);
    }

    public void unBookmark(Long boardId) {
        boardService.unBookmark(boardId);
    }

    public BoardIdDto updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = boardService.updateBoard(getIdWithThrow(), boardId, requestDto);
        return new BoardIdDto(board.getBoardId());
    }

    @Transactional
    public void deleteBoardByUser(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (!board.getUserId().equals(TokenUtil.getIdWithThrow())) {
            throw new ValidationException("작성자만 삭제 가능합니다.");
        }
        board.deleteByUser();
    }

    @Transactional
    public void setPin(Long boardId) {
        int pins = boardRepository.countByPinIsTrue();
        if (pins >= MAX_PIN_COUNT) {
            throw new ValidationException("고정은 " + MAX_PIN_COUNT + "개까지만 가능합니다.");
        }
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.setPin(TRUE);
    }

    @Transactional
    public void removePin(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.setPin(FALSE);
    }

    @Transactional
    public BoardIdDto createByAdmin(AdminBoardCreateRequestDto requestDto) {
        Board board = Board.builder()
                .userId(getIdWithThrow())
                .nickname(getNicknameWithThrow())
                .profileImageId(getProfileIdWithNull())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .boardType(requestDto.getBoardType())
                .fileEntities(fileService.saveAll(BOARD, requestDto.getAttachments()))
                .build();

        boardRepository.save(board);

        return new BoardIdDto(board.getBoardId());
    }

    public BoardIdDto updateBoardByAdmin(Long boardId, AdminBoardUpdateRequestDto requestDto) {
        Board board = boardService.updateBoardByAdmin(boardId, requestDto);
        return new BoardIdDto(board.getBoardId());
    }

    public void deleteBoardByAdmin(Long boardId) {
        boardService.deleteBoardByAdmin(boardId);
    }

    public PageContentsDto<List<AdminBoardListDto>> listByAdmin(AdminBoardsRequestDto requestDto) {
        Page<AdminBoardListDto> result = boardRepository.findAllByAdmin(requestDto);

        return PageContentsDto.of(result.getContent(), result.getTotalElements());
    }

    public void deleteBoardsByAdmin(DeleteBoardsRequestDto requestDto) {
        List<Long> boardIds = requestDto.getBoardIds();
        boardService.deleteBoardsByAdmin(boardIds);
    }
}
