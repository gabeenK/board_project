package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.combo.EventTypeDto;
import com.ukm.ssgb.dto.combo.EventTypesDto;
import com.ukm.ssgb.dto.combo.ScreenTypeDto;
import com.ukm.ssgb.dto.combo.ScreenTypesDto;
import com.ukm.ssgb.type.EventType;
import com.ukm.ssgb.type.ScreenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/combo")
public class AdminComboController {

    @GetMapping("/screenType")
    public ResponseEntity<ScreenTypesDto> getScreenTypes() {
        List<ScreenTypeDto> result = Arrays.stream(ScreenType.values()).map(ScreenTypeDto::toDto).toList();
        return ResponseEntity.ok(new ScreenTypesDto(result));
    }

    @GetMapping("/eventType")
    public ResponseEntity<EventTypesDto> getEventTypes() {
        List<EventTypeDto> result = Arrays.stream(EventType.values()).map(EventTypeDto::toDto).toList();
        return ResponseEntity.ok(new EventTypesDto(result));
    }
}
