package com.ukm.ssgb.service;

import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.type.FolderType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URLEncoder;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    private static final String PERMIT_FILE_EXTENSIONS = "xls,xlsx,ppt,pptx,doc,docx,pdf,zip,egg,hwp,jpg,jpeg,png,gif,csv";

    @SneakyThrows
    public String uploadFileToS3(FolderType folderType, MultipartFile file) {
        validateExtensions(file);
        
        String key = folderType.getUrl() + UUID.randomUUID() + "_" + URLEncoder.encode(Objects.requireNonNull(file.getOriginalFilename()), UTF_8);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .acl("public-read")
                .build();

        // S3에 파일 업로드
        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

        // 업로드된 파일의 key 반환
        return key;
    }

    @SneakyThrows
    public String download(String fileKey) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30)) // 유효 시간 설정
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url().toExternalForm();
    }
    
    public void validateExtensions(MultipartFile file) {
        String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (!PERMIT_FILE_EXTENSIONS.contains(Objects.requireNonNull(filenameExtension))) {
            throw new ValidationException("올바른 파일 형식이 아닙니다. 첨부가능한 파일 : Excel, PPT, Word, PDF, Zip, egg, hwp, csv, 이미지(JPG,JPEG,PNG,GIF)");
        }
    }
}
