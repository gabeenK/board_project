package com.ukm.ssgb.model;

import com.ukm.ssgb.dto.AttachmentDto;
import com.ukm.ssgb.type.BoardType;
import com.ukm.ssgb.type.DeletedType;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static com.ukm.ssgb.constant.ServiceConstants.*;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long boardId;

    @Column
    private Long userId;

    @Column
    private String nickname;

    @Column
    private Long profileImageId;

    @Setter
    @Column
    private String title;

    @Setter
    @Column
    private String content;

    @Column
    private Long viewCount;

    @Column
    private Long likeCount;

    @Column
    private Long claimCount;

    @Column
    @Enumerated(EnumType.STRING)
    private DeletedType deletedType;

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Setter
    @Column
    private Boolean pin;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardClaim> boardClaim = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLike> boardLikes = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardBookmark> boardBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardFile> boardFiles = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(Long userId, String nickname, Long profileImageId, String title, String content, BoardType boardType, List<FileEntity> fileEntities) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageId = profileImageId;
        this.title = title;
        this.content = content;
        this.viewCount = 0L;
        this.likeCount = 0L;
        this.claimCount = 0L;
        this.boardType = boardType;
        this.boardFiles = fileEntities.stream().map(fileEntity -> BoardFile.toEntity(this, fileEntity)).toList();
        this.deletedType = DeletedType.NONE;
        this.pin = Boolean.FALSE;
    }

    public boolean isDeleted() {
        return deletedType.isDeleted();
    }

    public boolean isHidden() {
        return claimCount >= CLAIM_MAX_COUNT;
    }

    public String getTitleForDisplay() {
        if (isDeleted()) {
            return this.deletedType.getBoardTitleByDeletedType(title);
        }
        if (isHidden()) {
            return HIDDEN_BOARD_TITLE;
        }
        return title;
    }

    public String getContentForDisplay() {
        if (isDeleted()) {
            return this.deletedType.getBoardContentByDeletedType(content);
        }
        if (isHidden()) {
            return HIDDEN_BOARD_CONTENT;
        }
        return content;
    }

    public List<AttachmentDto> getAttachmentsForDisplay() {
        if (isDeleted() || isHidden()) {
            return Collections.emptyList();
        }
        return boardFiles.stream().map(AttachmentDto::toDto).toList();
    }

    public void deleteByUser() {
        this.deletedType = DeletedType.BY_USER;
    }

    public void view() {
        viewCount++;
    }

    public void claim(BoardClaim boardclaim) {
        this.boardClaim.add(boardclaim);
        claimCount++;
    }

    public void like(BoardLike boardLike) {
        this.boardLikes.add(boardLike);
        likeCount++;
    }

    public void dislike(long userId) {
        boolean removed = this.boardLikes.removeIf(boardLike -> boardLike.getUserId().equals(userId));
        if (removed) {
            likeCount--;
        }
    }

    public void bookmark(BoardBookmark boardBookmark) {
        this.boardBookmarks.add(boardBookmark);
    }

    public void unBookmark(long userId) {
        this.boardBookmarks.removeIf(boardBookmark -> boardBookmark.getUserId().equals(userId));
    }

    public void deleteFiles(List<Long> deleteFileIds) {
        Set<Long> deleteFileIdSet = new HashSet<>(deleteFileIds);
        this.boardFiles.removeIf(boardFile -> deleteFileIdSet.contains(boardFile.getFileId()));
    }

    public void updateFiles(List<FileEntity> fileEntities) {
        if (fileEntities.isEmpty()) {
            return;
        }

        this.boardFiles.clear();
        fileEntities.stream()
                .map(file -> new BoardFile(this, file.getFileId()))
                .forEach(boardFile -> boardFiles.add(boardFile));
    }

    public void deleteByAdmin() {
        this.deletedType = DeletedType.BY_ADMIN;
    }
}
