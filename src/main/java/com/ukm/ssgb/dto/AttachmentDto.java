package com.ukm.ssgb.dto;

import com.ukm.ssgb.model.BoardFile;
import com.ukm.ssgb.model.ChatFile;
import com.ukm.ssgb.model.CommentFile;
import com.ukm.ssgb.model.EventFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class AttachmentDto {
    @Setter
    private String fileName;
    private Long fileId;
    @Setter
    private String filePath;

    public static AttachmentDto toDto(BoardFile boardFile) {
        return AttachmentDto.builder()
                .fileId(boardFile.getFileId())
                .build();
    }

    public static AttachmentDto toDto(CommentFile commentFile) {
        return AttachmentDto.builder()
                .fileId(commentFile.getFileId())
                .build();
    }

    public static AttachmentDto toDto(ChatFile chatFile) {
        return AttachmentDto.builder()
                .fileId(chatFile.getFileId())
                .build();
    }

    public static AttachmentDto toDto(EventFile eventFile) {
        return AttachmentDto.builder()
                .fileId(eventFile.getFileId())
                .build();
    }
}
