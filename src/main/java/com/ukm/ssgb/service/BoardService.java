package com.ukm.ssgb.service;

import com.ukm.ssgb.decorator.BoardListDecorator;
import com.ukm.ssgb.decorator.CommentListDecorator;
import com.ukm.ssgb.dto.board.AdminBoardUpdateRequestDto;
import com.ukm.ssgb.dto.board.BoardDetailDto;
import com.ukm.ssgb.dto.board.BoardUpdateRequestDto;
import com.ukm.ssgb.dto.comment.CommentDetailDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.*;
import com.ukm.ssgb.repository.BoardBookmarkRepository;
import com.ukm.ssgb.repository.BoardClaimRepository;
import com.ukm.ssgb.repository.BoardLikeRepository;
import com.ukm.ssgb.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ukm.ssgb.type.FolderType.BOARD;
import static com.ukm.ssgb.util.TokenUtil.getIdWithThrow;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardListDecorator boardListDecorator;
    private final CommentListDecorator commentListDecorator;
    private final FileService fileService;

    private final BoardRepository boardRepository;
    private final BoardClaimRepository boardClaimRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardBookmarkRepository boardBookmarkRepository;

    @Transactional(readOnly = true)
    public BoardDetailDto toBoardDetailDto(Board board) {
        BoardDetailDto boardDetailDto = BoardDetailDto.toDto(board);
        boardListDecorator.decorate(boardDetailDto);
        return boardDetailDto;
    }

    @Transactional(readOnly = true)
    public List<CommentDetailDto> organizeComments(List<Comment> comments) {
        List<CommentDetailDto> commentDtoList = toCommentDetailDto(comments);
        List<CommentDetailDto> parentCommentDtoList = organizeParentList(commentDtoList);
        addChildComment(parentCommentDtoList, commentDtoList);
        return parentCommentDtoList;
    }

    private List<CommentDetailDto> toCommentDetailDto(List<Comment> comments) {
        List<CommentDetailDto> commentList = comments.stream().map(CommentDetailDto::toDto).toList();
        commentList.forEach(commentListDecorator::decorate);
        return commentList;
    }

    private List<CommentDetailDto> organizeParentList(List<CommentDetailDto> commentDetailDtoList) {
        return commentDetailDtoList.stream()
                .filter(dto -> isNull(dto.getParentCommentId()))
                .toList();
    }

    private void addChildComment(List<CommentDetailDto> parentCommentDtoList, List<CommentDetailDto> commentDtoList) {
        parentCommentDtoList.forEach(parentDto -> parentDto.setComments(
                commentDtoList.stream()
                        .filter(commentDto -> parentDto.getCommentId().equals(commentDto.getParentCommentId()))
                        .toList()
        ));
    }

    @Transactional
    public void claim(Long boardId) {
        Optional<BoardClaim> boardClaim = boardClaimRepository.findByBoardIdAndUserId(boardId, getIdWithThrow());
        if (boardClaim.isPresent()) {
            throw new ValidationException("이미 신고한 게시글입니다.");
        }

        Board board = boardRepository.findById(boardId).orElseThrow();
        board.claim(new BoardClaim(board, getIdWithThrow()));
    }

    @Transactional
    public void like(Long boardId) {
        Optional<BoardLike> boardLike = boardLikeRepository.findByBoardIdAndUserId(boardId, getIdWithThrow());
        if (boardLike.isPresent()) {
            return;
        }

        Board board = boardRepository.findById(boardId).orElseThrow();
        board.like(new BoardLike(board, getIdWithThrow()));
    }

    @Transactional
    public void dislike(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.dislike(getIdWithThrow());
    }

    @Transactional
    public void bookmark(Long boardId) {
        Optional<BoardBookmark> boardBookmark = boardBookmarkRepository.findByBoardIdAndUserId(boardId, getIdWithThrow());
        if (boardBookmark.isPresent()) {
            return;
        }

        Board board = boardRepository.findById(boardId).orElseThrow();
        board.bookmark(new BoardBookmark(board, getIdWithThrow()));
    }

    @Transactional
    public void unBookmark(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.unBookmark(getIdWithThrow());
    }

    @Transactional
    public Board updateBoard(long userId, long boardId, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findByBoardIdAndUserId(boardId, userId).orElseThrow();

        board.setBoardType(requestDto.getBoardType());
        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.deleteFiles(requestDto.getDeleteFileIds());
        board.updateFiles(fileService.saveAll(BOARD, requestDto.getAttachments()));

        return board;
    }

    @Transactional
    public Board updateBoardByAdmin(Long boardId, AdminBoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        board.setBoardType(requestDto.getBoardType());
        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.deleteFiles(requestDto.getDeleteFileIds());
        board.updateFiles(fileService.saveAll(BOARD, requestDto.getAttachments()));

        return board;
    }

    @Transactional
    public void deleteBoardByAdmin(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.deleteByAdmin();
    }

    @Transactional
    public void deleteBoardsByAdmin(List<Long> boardIds) {
        List<Board> boards = boardRepository.findAllById(boardIds);
        boards.forEach(Board::deleteByAdmin);
    }
}
