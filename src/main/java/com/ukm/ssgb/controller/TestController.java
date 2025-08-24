package com.ukm.ssgb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ukm.ssgb.model.Test;
import com.ukm.ssgb.service.TestService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
  private TestService testService;

  @PostMapping("/list")
  public List<Test> getAllTest() {
    return testService.findAll();
  }

  @PostMapping("/add")
  public Test createTest(@RequestBody Test test) {
    test.setRegDate(LocalDateTime.now());
    return testService.save(test);
  }

  @PostMapping(value = "/addWithFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Test createTestWithFile(
      @RequestPart("test") Test test,
      @RequestPart("file") MultipartFile file) throws IOException {
    test.setRegDate(LocalDateTime.now());
    return testService.save(test, file);
  }

}
