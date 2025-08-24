package com.ukm.ssgb.dto;

import com.ukm.ssgb.decorator.code.FilePathCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BusinessRegistrationDto implements FilePathCode {
    private final Long fileId;
    private final String fileName;
    private final String fileExt;
    @Setter
    private String filePath;

    @Builder
    private BusinessRegistrationDto(Long fileId, String fileName, String fileExt, String filePath) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.filePath = filePath;
    }

    public static BusinessRegistrationDto of(FileDto fileDto) {
        return BusinessRegistrationDto.builder()
                .fileId(fileDto.getFileId())
                .fileName(fileDto.getFileName())
                .fileExt(fileDto.getFileExt())
                .filePath(fileDto.getFilePath())
                .build();
    }

    public static BusinessRegistrationDto empty() {
        return BusinessRegistrationDto.builder()
                .build();
    }
}