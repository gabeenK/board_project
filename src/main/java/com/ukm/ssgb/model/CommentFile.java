package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "comment_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long commentFileId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    @Column
    private Long fileId;

    public CommentFile(Comment comment, FileEntity file) {
        this.comment = comment;
        this.fileId = file.getFileId();
    }
}