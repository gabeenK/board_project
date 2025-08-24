package com.ukm.ssgb.dto.event;

import com.ukm.ssgb.constant.ServiceConstants;
import com.ukm.ssgb.type.EventType;
import com.ukm.ssgb.type.ScreenType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ukm.ssgb.constant.ServiceConstants.MAX_ATTACHMENT_FILE_SIZE;

@Getter
@Setter
public class EventCreateRequestDto {

    @NotBlank
    private String title;

    @NotNull
    private ScreenType screenType;

    @NotNull
    private EventType eventType;

    private String link;

    @NotNull
    private Boolean always;

    @FutureOrPresent
    private LocalDateTime startAt;

    @Future
    private LocalDateTime endAt;

    @Size(max = MAX_ATTACHMENT_FILE_SIZE)
    List<MultipartFile> attachments = new ArrayList<>();

    @NotNull
    private Boolean active;

    private String content;
}
