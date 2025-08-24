package com.ukm.ssgb.dto.usermanagement;

import com.ukm.ssgb.dto.page.PageRequestDto;
import lombok.*;

@Getter
@Setter
public class GetUsersRequestDto extends PageRequestDto {

    private Boolean approved;
    private String query;
}
