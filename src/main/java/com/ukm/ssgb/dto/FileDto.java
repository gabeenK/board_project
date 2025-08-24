package com.ukm.ssgb.dto;

import com.ukm.ssgb.model.FileEntity;
import lombok.Getter;

@Getter
public class FileDto {
    Long fileId;
    String fileName;
    String fileExt;
    String filePath;

    public FileDto(FileEntity fileEntity) {
        this.fileId = fileEntity.getFileId();
        this.fileName = fileEntity.getFileName();
        this.fileExt = fileEntity.getFileExt();
        this.filePath = fileEntity.getFilePath();
    }
}