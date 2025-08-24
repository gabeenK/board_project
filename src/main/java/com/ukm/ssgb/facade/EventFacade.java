package com.ukm.ssgb.facade;

import com.ukm.ssgb.decorator.filler.FileNameFiller;
import com.ukm.ssgb.decorator.filler.FilePathFiller;
import com.ukm.ssgb.dto.event.*;
import com.ukm.ssgb.dto.page.PageContentsDto;
import com.ukm.ssgb.model.Event;
import com.ukm.ssgb.repository.EventRepository;
import com.ukm.ssgb.service.FileService;
import com.ukm.ssgb.type.ScreenType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ukm.ssgb.constant.ServiceConstants.ALWAYS_END_DATE;
import static com.ukm.ssgb.constant.ServiceConstants.ALWAYS_START_DATE;
import static com.ukm.ssgb.type.FolderType.EVENT;

@Service
@RequiredArgsConstructor
public class EventFacade {

    private final FileService fileService;
    private final EventRepository eventRepository;
    private final FilePathFiller filePathFiller;
    private final FileNameFiller fileNameFiller;

    @Transactional(readOnly = true)
    public List<EventListByUserDto> listByUser(ScreenType screenType) {
        return eventRepository.findActiveEventByScreenType(screenType);
    }

    @Transactional(readOnly = true)
    public PageContentsDto<List<EventListDto>> listByAdmin(Boolean active, PageRequest pageRequest) {
        Page<EventListDto> eventList = eventRepository.findAllByActive(active, pageRequest);

        return PageContentsDto.of(eventList.getContent(), eventList.getTotalElements());
    }

    @Transactional
    public EventIdDto create(EventCreateRequestDto requestDto) {
        Event event = Event.builder().title(requestDto.getTitle())
                .screenType(requestDto.getScreenType())
                .eventType(requestDto.getEventType())
                .startAt(requestDto.getAlways() ? ALWAYS_START_DATE : requestDto.getStartAt())
                .endAt(requestDto.getAlways() ? ALWAYS_END_DATE : requestDto.getEndAt())
                .link(requestDto.getLink())
                .fileEntities(fileService.saveAll(EVENT, requestDto.getAttachments()))
                .active(requestDto.getActive())
                .content(requestDto.getContent())
                .build();

        eventRepository.save(event);

        return new EventIdDto(event.getEventId());
    }

    @Transactional(readOnly = true)
    public EventDetailDto get(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        EventDetailDto eventDetailDto = EventDetailDto.toDto(event);

        fileNameFiller.fill(eventDetailDto.getAttachments());

        return eventDetailDto;
    }

    @Transactional
    public EventIdDto update(Long eventId, EventUpdateRequestDto requestDto) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setTitle(requestDto.getTitle());
        event.setScreenType(requestDto.getScreenType());
        event.setEventType(requestDto.getEventType());
        event.setLink(requestDto.getLink());
        event.setStartAt(requestDto.getAlways() ? ALWAYS_START_DATE : requestDto.getStartAt());
        event.setEndAt(requestDto.getAlways() ? ALWAYS_END_DATE : requestDto.getEndAt());
        event.deleteFiles(requestDto.getDeleteFileIds());
        event.updateFiles(fileService.saveAll(EVENT, requestDto.getAttachments()));
        event.setActive(requestDto.getActive());
        event.setContent(requestDto.getContent());

        return new EventIdDto(event.getEventId());
    }

    @Transactional
    public void delete(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.delete();
    }
}
