package com.ukm.ssgb.decorator;

import com.ukm.ssgb.decorator.filler.CommentClaimFiller;
import com.ukm.ssgb.decorator.filler.CommentLikeFiller;
import com.ukm.ssgb.decorator.filler.FileNameFiller;
import com.ukm.ssgb.dto.comment.CommentDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentListDecorator {
    private final CommentLikeFiller commentLikeFiller;
    private final CommentClaimFiller commentClaimFiller;
    private final FileNameFiller fileNameFiller;

    public void decorate(CommentDetailDto dto) {
        if (dto == null) {
            return;
        }
        fileNameFiller.fill(dto.getAttachments());
        commentLikeFiller.fill(dto);
        commentClaimFiller.fill(dto);
    }
}