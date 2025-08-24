package com.ukm.ssgb.repository;

import com.ukm.ssgb.dto.event.EventListByUserDto;
import com.ukm.ssgb.dto.event.EventListDto;
import com.ukm.ssgb.type.ScreenType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomEventRepository {

    Page<EventListDto> findAllByActive(Boolean active, Pageable pageable);

    List<EventListByUserDto> findActiveEventByScreenType(ScreenType screenType);
}
