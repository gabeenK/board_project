package com.ukm.ssgb.util;

import java.time.Duration;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Slf4j
@Component
public class AwsS3Util {

  private final S3Presigner presigner;
  private final S3Client s3Client;
  private static final Set<String> ALLOWED_EXTENSIONS = Set.of("png", "jpg", "jpeg", "pdf");

  public AwsS3Util(S3Presigner presigner, S3Client s3Client) {
    this.presigner = presigner;
    this.s3Client = s3Client;
  }

  public String createPresignedGetUrl(String bucketName, String keyName) {
    // 키 Empty, Null 확인
    if (!StringUtils.hasText(keyName)) {
      log.error("KeyName is empty or null.");
      return keyName;
    }

    // 파일 확장자 검사
    String fileExtension = StringUtils.getFilenameExtension(keyName);
    if (fileExtension == null || !ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
      log.info("Unsupported file extension: [{}]. Returning keyName.", fileExtension);
      return keyName;
    }

    // S3 버킷에서 객체 존재 여부 확인
    if (!doesObjectExist(bucketName, keyName)) {
      log.warn("S3 object not found: bucket={}, key={}. Returning keyName.", bucketName, keyName);
      return keyName;
    }

    try {
      GetObjectRequest objectRequest = GetObjectRequest.builder()
          .bucket(bucketName)
          .key(keyName)
          .build();

      GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
          .signatureDuration(Duration.ofMinutes(30)) // 유효 시간 설정
          .getObjectRequest(objectRequest)
          .build();

      PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
      log.info("Presigned URL: [{}]", presignedRequest.url().toString());
      return presignedRequest.url().toExternalForm();
    } catch (Exception e) {
      log.error("Failed to create presigned URL for bucket: {}, key: {}. Exception: {}", bucketName, keyName,
          e.getMessage(), e);
      return keyName;
    }
  }

  private boolean doesObjectExist(String bucketName, String keyName) {
    try {
      HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
          .bucket(bucketName)
          .key(keyName)
          .build();
      s3Client.headObject(headObjectRequest);
      return true;
    } catch (NoSuchKeyException e) {
      log.warn("Object does not exist in S3: bucket={}, key={}", bucketName, keyName);
      return false;
    } catch (Exception e) {
      log.error("Error checking object existence: bucket={}, key={}. Exception: {}", bucketName, keyName,
          e.getMessage(), e);
      return false;
    }
  }

}
