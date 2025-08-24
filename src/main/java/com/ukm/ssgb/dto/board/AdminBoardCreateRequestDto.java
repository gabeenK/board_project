package com.ukm.ssgb.dto.board;

import com.ukm.ssgb.type.BoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.ukm.ssgb.constant.ServiceConstants.MAX_ATTACHMENT_FILE_SIZE;

@Getter
@Setter
public class AdminBoardCreateRequestDto {

    @NotNull
    private BoardType boardType;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Size(max = MAX_ATTACHMENT_FILE_SIZE)
    List<MultipartFile> attachments = new ArrayList<>();
}
