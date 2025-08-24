package com.ukm.ssgb.facade;

import com.ukm.ssgb.dto.TokenDto;
import com.ukm.ssgb.dto.comment.*;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.model.Comment;
import com.ukm.ssgb.repository.CommentRepository;
import com.ukm.ssgb.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.ukm.ssgb.util.TokenUtil.getIdWithThrow;
import static com.ukm.ssgb.util.TokenUtil.getUserInfo;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    public CommentIdDto createComment(CommentCreateRequestDto requestDto, TokenDto userInfo) {
        Long boardId = requestDto.getBoardId();
        String content = requestDto.getContent();
        List<MultipartFile> attachments = requestDto.getAttachments();

        Comment comment = commentService.createComment(boardId, content, attachments, userInfo);

        return new CommentIdDto(comment.getCommentId());
    }

    public CommentIdDto createChildComment(Long parentCommentId, ChildCommentCreateRequestDto requestDto, TokenDto userInfo) {
        Long boardId = requestDto.getBoardId();
        String content = requestDto.getContent();
        List<MultipartFile> attachments = requestDto.getAttachments();

        Comment comment = commentService.createChildComment(boardId, parentCommentId, content, attachments, userInfo);

        return new CommentIdDto(comment.getCommentId());
    }

    public void claim(Long commentId) {
        commentService.claim(commentId);
    }

    public void like(Long commentId) {
        commentService.like(commentId);
    }

    public void dislike(Long commentId) {
        commentService.dislike(commentId);
    }

    public CommentIdDto updateComment(Long commentId, CommentUpdateRequestDto requestDto) {
        String content = requestDto.getContent();
        List<MultipartFile> attachments = requestDto.getAttachments();
        List<Long> deleteFileIds = requestDto.getDeleteFileIds();
        Comment comment = commentService.updateComment(getIdWithThrow(), commentId, content, attachments, deleteFileIds);
        return new CommentIdDto(comment.getCommentId());
    }

    public void deleteCommentByUser(Long commentId) {
        commentService.deleteCommentByUser(commentId);
    }

    public CommentIdDto createCommentByAdmin(AdminCreateCommentRequestDto requestDto) {
        Long boardId = requestDto.getBoardId();
        String content = requestDto.getContent();
        Long parentCommentId = requestDto.getParentCommentId();
        List<MultipartFile> attachments = Collections.emptyList();
        TokenDto userInfo = Objects.requireNonNull(getUserInfo());

        Comment comment = Objects.isNull(parentCommentId)
                ? commentService.createComment(boardId, content, attachments, userInfo)
                : commentService.createChildComment(boardId, parentCommentId, content, attachments, userInfo);

        return new CommentIdDto(comment.getCommentId());
    }

    public void deleteCommentByAdmin(Long commentId) {
        commentService.deleteCommentByAdmin(commentId);
    }

    public void deleteCommentsByAdmin(DeleteCommentsRequestDto requestDto) {
        List<Long> commentIds = requestDto.getCommentIds();
        commentService.deleteCommentsByAdmin(commentIds);
    }

    public PageContentsDto<List<AdminCommentListDto>> listByAdmin(AdminCommentsRequestDto requestDto) {
        Page<AdminCommentListDto> parentCommentsPageable = commentRepository.findAllByAdmin(requestDto);
        List<AdminCommentListDto> parentComments = parentCommentsPageable.getContent();

        addChildComments(parentComments);

        return PageContentsDto.of(parentComments, parentCommentsPageable.getTotalElements());
    }

    private void addChildComments(List<AdminCommentListDto> parentComments) {
        Map<Long, List<AdminCommentListDto>> childCommentsMap = getChildCommentsMap(parentComments);

        for (AdminCommentListDto parentComment : parentComments) {
            parentComment.setComments(childCommentsMap.getOrDefault(parentComment.getCommentId(), Collections.emptyList()));
        }
    }

    private Map<Long, List<AdminCommentListDto>> getChildCommentsMap(List<AdminCommentListDto> parentComments) {
        List<Long> parentCommentIds = parentComments.stream().map(AdminCommentListDto::getCommentId).toList();
        return commentRepository.findAllChildCommentByAdmin(parentCommentIds)
                .stream()
                .collect(Collectors.groupingBy(AdminCommentListDto::getParentCommentId));
    }
}
