package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.event.EventListByUserDto;
import com.ukm.ssgb.facade.EventFacade;
import com.ukm.ssgb.type.ScreenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventFacade eventFacade;

    @GetMapping
    public ResponseEntity<List<EventListByUserDto>> eventList(@RequestParam(value = "screenType") ScreenType screenType) {
        List<EventListByUserDto> result = eventFacade.listByUser(screenType);
        return ResponseEntity.ok(result);
    }
}
