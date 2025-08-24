package com.ukm.ssgb.util;

import com.ukm.ssgb.dto.page.PageRequestDto;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    public static <T> List<T> paging(List<T> list, PageRequestDto pageRequestDto) {
        if (pageRequestDto == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        Integer page = pageRequestDto.getPageNumber();
        Integer size = pageRequestDto.getSize();

        int start = page * size;
        int end = Math.min(start + size, list.size());

        return list.subList(start, end);
    }
}