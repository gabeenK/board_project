package com.ukm.ssgb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "test")
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "reg_date", nullable = false)
  private LocalDateTime regDate;

  @Column(nullable = false)
  private Integer category;

  @Column(nullable = true)
  private String title;

  @Column(nullable = true)
  private String content;

  @Column(name = "img_path", nullable = true)
  private String imgPath;

  public Test() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getRegDate() {
    return regDate;
  }

  public void setRegDate(LocalDateTime dateTime) {
    this.regDate = dateTime;
  }

  public Integer getCategory() {
    return category;
  }

  public void setCategory(Integer category) {
    this.category = category;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }
}
