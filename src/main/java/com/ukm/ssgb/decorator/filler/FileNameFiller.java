package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.dto.AttachmentDto;
import com.ukm.ssgb.dto.FileDto;
import com.ukm.ssgb.service.FileService;
import com.ukm.ssgb.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class FileNameFiller {

    private final FileService fileService;
    private final S3FileService s3FileService;

    public void fill(AttachmentDto dto) {
        if (dto == null || isNull(dto.getFileId())) {
            return;
        }

        FileDto fileDto = fileService.getFile(dto.getFileId());
        dto.setFileName(fileDto.getFileName());
        dto.setFilePath(s3FileService.download(fileDto.getFilePath()));
    }

    public void fill(List<AttachmentDto> dtoList) {
        dtoList.forEach(this::fill);
    }
}