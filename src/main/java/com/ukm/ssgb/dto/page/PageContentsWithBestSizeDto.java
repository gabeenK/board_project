package com.ukm.ssgb.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageContentsWithBestSizeDto<T> {
    private T contents;
    private Long totalCount;
    private Integer bestSize;

    public static <T> PageContentsWithBestSizeDto<T> of(T contents, Long totalCount, Integer bestSize) {
        return new PageContentsWithBestSizeDto<>(contents, totalCount, bestSize);
    }
}
