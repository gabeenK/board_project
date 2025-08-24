package com.ukm.ssgb.dto.board;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.ukm.ssgb.constant.ServiceConstants.MAX_DELETE_BOARD_SIZE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteBoardsRequestDto {

    @Size(max = MAX_DELETE_BOARD_SIZE)
    private final List<Long> boardIds = new ArrayList<>();
}
