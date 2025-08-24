package com.ukm.ssgb.service;

import com.ukm.ssgb.dto.FileDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.FileEntity;
import com.ukm.ssgb.repository.FileEntityRepository;
import com.ukm.ssgb.type.CacheName;
import com.ukm.ssgb.type.FolderType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileEntityRepository fileEntityRepository;
    private final S3FileService s3FileService;

    @Transactional
    public List<FileEntity> saveAll(FolderType folderType, List<MultipartFile> multipartFiles) {
        List<FileEntity> fileEntities = multipartFiles.stream()
                .filter(file -> !file.isEmpty())
                .map(file -> this.toFileEntity(folderType, file))
                .toList();

        return fileEntityRepository.saveAll(fileEntities);
    }

    @Transactional
    public FileEntity save(FolderType folderType, MultipartFile multipartFile) {
        validateEmpty(multipartFile);

        return fileEntityRepository.save(toFileEntity(folderType, multipartFile));
    }

    private static void validateEmpty(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ValidationException("파일이 없습니다.");
        }
    }

    private FileEntity toFileEntity(FolderType folderType, MultipartFile multipartFile) {
        String filePath = s3FileService.uploadFileToS3(folderType, multipartFile);

        return FileEntity.builder()
                .fileName(multipartFile.getOriginalFilename())
                .fileExt(StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()))
                .fileSize(multipartFile.getSize())
                .filePath(filePath)
                .build();
    }

    @Cacheable(value = CacheName.FILE, key = "#fileId")
    public FileDto getFile(Long fileId) {
        return fileEntityRepository.findById(fileId)
                .map(FileDto::new)
                .orElseThrow();
    }
}
