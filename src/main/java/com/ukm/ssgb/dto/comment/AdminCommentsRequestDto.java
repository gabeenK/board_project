package com.ukm.ssgb.dto.comment;

import com.ukm.ssgb.dto.page.PageRequestDto;
import com.ukm.ssgb.type.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCommentsRequestDto extends PageRequestDto {

    private BoardType boardType;

    private Boolean hidden;

    private Boolean deleted;

    private String query;
}
