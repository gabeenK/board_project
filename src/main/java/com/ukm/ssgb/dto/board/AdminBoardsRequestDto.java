package com.ukm.ssgb.dto.board;

import com.ukm.ssgb.dto.page.PageRequestDto;
import com.ukm.ssgb.type.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBoardsRequestDto extends PageRequestDto {

    private BoardType boardType;

    private Boolean hidden;

    private Boolean deleted;

    private BoardSort sort = BoardSort.createdAt;

    private String query;

    public enum BoardSort {
        view,
        like,
        createdAt
    }
}
