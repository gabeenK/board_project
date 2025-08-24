package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.event.*;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.dto.page.PageRequestDto;
import com.ukm.ssgb.facade.EventFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/event")
public class AdminEventController {

    private final EventFacade eventFacade;

    @GetMapping
    public ResponseEntity<PageContentsDto<List<EventListDto>>> eventList(@RequestParam(value = "active", required = false) Boolean active,
                                                                         @Valid PageRequestDto pageRequestDto) {
        PageContentsDto<List<EventListDto>> result = eventFacade.listByAdmin(active, pageRequestDto.getPageRequest());
        return ResponseEntity.ok(result);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventIdDto> createEvent(@Valid @ModelAttribute EventCreateRequestDto requestDto) {
        EventIdDto result = eventFacade.create(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{eventId}")
    public ResponseEntity<EventDetailDto> getEvent(@PathVariable("eventId") Long eventId) {
        EventDetailDto result = eventFacade.get(eventId);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventIdDto> updateEvent(@PathVariable("eventId") Long eventId,
                                                  @Valid @ModelAttribute EventUpdateRequestDto requestDto) {
        EventIdDto result = eventFacade.update(eventId, requestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") Long eventId) {
        eventFacade.delete(eventId);
        return ResponseEntity.ok().build();
    }
}
