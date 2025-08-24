package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.comment.*;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.facade.CommentFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/comment")
public class AdminCommentController {

    private final CommentFacade commentFacade;

    @GetMapping
    public ResponseEntity<PageContentsDto<List<AdminCommentListDto>>> getComments(@Valid AdminCommentsRequestDto requestDto) {
        return ResponseEntity.ok(commentFacade.listByAdmin(requestDto));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommentIdDto> createComment(@Valid @ModelAttribute AdminCreateCommentRequestDto requestDto) {
        CommentIdDto result = commentFacade.createCommentByAdmin(requestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComments(@Valid @RequestBody DeleteCommentsRequestDto requestDto) {
        commentFacade.deleteCommentsByAdmin(requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        commentFacade.deleteCommentByAdmin(commentId);
        return ResponseEntity.ok().build();
    }
}
