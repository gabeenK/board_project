package com.ukm.ssgb.dto.page;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
public class PageRequestDto {
    @NotNull
    @Positive
    Integer page;

    @NotNull
    @Positive
    Integer size;

    public PageRequest getPageRequest() {
        return PageRequest.of(this.page - 1, this.size);
    }

    public Integer getPageNumber() {
        return this.page - 1;
    }
}
