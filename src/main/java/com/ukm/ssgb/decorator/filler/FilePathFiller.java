package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.FilePathCode;
import com.ukm.ssgb.decorator.code.ProfileImageFilePathCode;
import com.ukm.ssgb.dto.FileDto;
import com.ukm.ssgb.service.FileService;
import com.ukm.ssgb.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class FilePathFiller {

    private final FileService fileService;
    private final S3FileService s3FileService;

    public void fill(FilePathCode dto) {
        if (dto == null || isNull(dto.getFileId())) {
            return;
        }

        FileDto fileDto = fileService.getFile(dto.getFileId());
        dto.setFilePath(fileDto.getFilePath());
        dto.setFilePath(s3FileService.download(fileDto.getFilePath()));
    }

    public void fill(ProfileImageFilePathCode dto) {
        if (dto == null || isNull(dto.getProfileImageId())) {
            return;
        }

        FileDto fileDto = fileService.getFile(dto.getProfileImageId());
        dto.setFilePath(s3FileService.download(fileDto.getFilePath()));
    }
}