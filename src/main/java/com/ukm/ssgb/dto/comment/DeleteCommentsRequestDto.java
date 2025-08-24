package com.ukm.ssgb.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.ukm.ssgb.constant.ServiceConstants.MAX_DELETE_COMMENT_SIZE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCommentsRequestDto {

    @Size(max = MAX_DELETE_COMMENT_SIZE)
    private final List<Long> commentIds = new ArrayList<>();
}
