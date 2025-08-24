package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.comment.ChildCommentCreateRequestDto;
import com.ukm.ssgb.dto.comment.CommentCreateRequestDto;
import com.ukm.ssgb.dto.comment.CommentIdDto;
import com.ukm.ssgb.dto.comment.CommentUpdateRequestDto;
import com.ukm.ssgb.facade.CommentFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ukm.ssgb.util.TokenUtil.getUserInfo;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommentIdDto> createComment(@Valid @ModelAttribute CommentCreateRequestDto requestDto) {
        CommentIdDto result = commentFacade.createComment(requestDto, getUserInfo());
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/{commentId}/comment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommentIdDto> createChildComment(@PathVariable("commentId") Long parentCommentId,
                                                           @Valid @ModelAttribute ChildCommentCreateRequestDto requestDto) {
        CommentIdDto result = commentFacade.createChildComment(parentCommentId, requestDto, getUserInfo());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{commentId}/claim")
    public ResponseEntity<Void> claim(@PathVariable("commentId") Long commentId) {
        commentFacade.claim(commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<Void> like(@PathVariable("commentId") Long commentId) {
        commentFacade.like(commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<Void> dislike(@PathVariable("commentId") Long commentId) {
        commentFacade.dislike(commentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentIdDto> updateComment(@PathVariable("commentId") Long commentId,
                                                      @Valid @ModelAttribute CommentUpdateRequestDto requestDto) {
        CommentIdDto result = commentFacade.updateComment(commentId, requestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        commentFacade.deleteCommentByUser(commentId);
        return ResponseEntity.ok().build();
    }
}
