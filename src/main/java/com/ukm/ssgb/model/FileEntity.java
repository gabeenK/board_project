package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long fileId;

    @Column
    private String fileName;

    @Column
    private String fileExt;

    @Column
    private Long fileSize;

    @Column
    private String filePath;

    @Builder
    public FileEntity(String fileName, String fileExt, Long fileSize, String filePath) {
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.fileSize = fileSize;
        this.filePath = filePath;
    }
}