package com.ukm.ssgb.dto.page;

public record PageContentsDto<T>(T contents, Long totalCount) {

    public static <T> PageContentsDto<T> of(T contents, Long totalCount) {
        return new PageContentsDto<>(contents, totalCount);
    }
}
