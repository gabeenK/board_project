package com.ukm.ssgb.dto.usermanagement;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.ukm.ssgb.constant.ServiceConstants.MAX_DELETE_USER_SIZE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteUsersRequestDto {

    @Size(max = MAX_DELETE_USER_SIZE)
    private final List<Long> userIds = new ArrayList<>();
}
