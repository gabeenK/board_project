package com.ukm.ssgb.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.ukm.ssgb.constant.ServiceConstants.MAX_ATTACHMENT_FILE_SIZE;

@Getter
@Setter
public class CommentUpdateRequestDto {

    private String content;

    @Size(max = MAX_ATTACHMENT_FILE_SIZE)
    private List<MultipartFile> attachments = new ArrayList<>();

    @Size(max = MAX_ATTACHMENT_FILE_SIZE)
    private List<Long> deleteFileIds = new ArrayList<>();
}
