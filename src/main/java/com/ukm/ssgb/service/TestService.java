package com.ukm.ssgb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ukm.ssgb.model.Test;
import com.ukm.ssgb.repository.TestRepository;
import com.ukm.ssgb.util.AwsS3Util;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestService {

  private final S3Client s3Client;
  private final AwsS3Util awsS3Util;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  @Autowired
  private TestRepository testRepository;

  @Autowired
  public TestService(S3Client s3Client, AwsS3Util awsS3Util) {
    this.s3Client = s3Client;
    this.awsS3Util = awsS3Util;
  }

  public List<Test> findAll() {
    List<Test> tests = testRepository.findAll();
    tests.stream().forEach(t -> t.setImgPath(awsS3Util.createPresignedGetUrl(bucketName, t.getImgPath())));
    return tests;
  }

  public Test save(Test test) {
    return testRepository.save(test);
  }

  public Test save(Test test, MultipartFile file) throws IOException {
    // 파일이 있을 경우 S3에 업로드하고 Key 반환
    if (file != null && !file.isEmpty()) {
      String imgPath = uploadFileToS3(file);
      test.setImgPath(imgPath); // S3 파일 Key를 Test 객체에 설정
    }
    return testRepository.save(test); // Test 객체 저장
  }

  private String uploadFileToS3(MultipartFile file) throws IOException {
    String key = "uploads/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

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

}
