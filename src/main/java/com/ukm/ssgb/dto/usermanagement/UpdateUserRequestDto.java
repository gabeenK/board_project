package com.ukm.ssgb.dto.usermanagement;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {

    @NotNull
    private Boolean approved;
}
