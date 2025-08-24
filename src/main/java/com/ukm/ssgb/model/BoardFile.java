package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "board_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long boardFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @Column
    private Long fileId;

    @Builder
    public BoardFile(Board board, Long fileId) {
        this.board = board;
        this.fileId = fileId;
    }

    public static BoardFile toEntity(Board board, FileEntity fileEntity) {
        return BoardFile.builder()
                .board(board)
                .fileId(fileEntity.getFileId())
                .build();
    }
}