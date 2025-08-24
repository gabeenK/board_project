package com.ukm.ssgb.model;

import com.ukm.ssgb.dto.AttachmentDto;
import com.ukm.ssgb.type.DeletedType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ukm.ssgb.constant.ServiceConstants.CLAIM_MAX_COUNT;
import static com.ukm.ssgb.constant.ServiceConstants.HIDDEN_COMMENT_CONTENT;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long commentId;

    @Column
    private Long parentCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @Column
    private Long userId;

    @Column
    private String nickname;

    @Column
    private Long profileImageId;

    @Setter
    @Column
    private String content;

    @Column
    private Long likeCount;

    @Column
    private Long claimCount;

    @Column
    @Enumerated(EnumType.STRING)
    private DeletedType deletedType;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentClaim> commentClaims;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentFile> commentFiles;

    @Builder
    public Comment(Long parentCommentId,
                   Board board,
                   Long userId,
                   String nickname,
                   Long profileImageId,
                   String content,
                   List<FileEntity> fileEntities) {
        this.parentCommentId = parentCommentId;
        this.board = board;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageId = profileImageId;
        this.content = content;
        this.likeCount = 0L;
        this.claimCount = 0L;
        this.deletedType = DeletedType.NONE;
        this.commentFiles = fileEntities.stream().map(file -> new CommentFile(this, file)).toList();
    }

    public boolean isDeleted() {
        return this.deletedType.isDeleted();
    }

    public boolean isHidden() {
        return claimCount >= CLAIM_MAX_COUNT;
    }

    public String getContentForDisplay() {
        if (isDeleted()) {
            return this.deletedType.getCommentContentByDeletedType(content);
        }
        if (isHidden()) {
            return HIDDEN_COMMENT_CONTENT;
        }
        return content;
    }

    public List<AttachmentDto> getAttachmentsForDisplay() {
        if (isDeleted() || isHidden()) {
            return Collections.emptyList();
        }
        return commentFiles.stream().map(AttachmentDto::toDto).toList();
    }

    public void deleteByUser() {
        this.deletedType = DeletedType.BY_USER;
    }

    public void deleteByAdmin() {
        this.deletedType = DeletedType.BY_ADMIN;
    }

    public void claim(CommentClaim commentClaim) {
        this.commentClaims.add(commentClaim);
        claimCount++;
    }

    public void like(CommentLike commentLike) {
        this.commentLikes.add(commentLike);
        likeCount++;
    }

    public void dislike(long userId) {
        boolean removed = this.commentLikes.removeIf(commentLike -> commentLike.getUserId().equals(userId));
        if (removed) {
            likeCount--;
        }
    }

    public void deleteFiles(List<Long> deleteFileIds) {
        Set<Long> deleteFileIdSet = new HashSet<>(deleteFileIds);
        this.commentFiles.removeIf(commentFile -> deleteFileIdSet.contains(commentFile.getFileId()));
    }

    public void updateFiles(List<FileEntity> fileEntities) {
        if (fileEntities.isEmpty()) {
            return;
        }

        this.commentFiles.clear();
        fileEntities.stream()
                .map(file -> new CommentFile(this, file))
                .forEach(commentFile -> commentFiles.add(commentFile));
    }
}