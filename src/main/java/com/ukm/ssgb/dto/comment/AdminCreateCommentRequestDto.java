package com.ukm.ssgb.dto.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateCommentRequestDto {
    @NotNull
    @Positive
    private Long boardId;

    private Long parentCommentId;

    private String content;
}
