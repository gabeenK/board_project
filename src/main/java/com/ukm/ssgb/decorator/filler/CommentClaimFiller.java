package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.CommentClaimCode;
import com.ukm.ssgb.model.CommentClaim;
import com.ukm.ssgb.repository.CommentClaimRepository;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentClaimFiller {

    private final CommentClaimRepository commentClaimRepository;

    public void fill(CommentClaimCode dto) {
        if (dto == null || Objects.isNull(TokenUtil.getIdWithNull())) {
            return;
        }

        Optional<CommentClaim> commentClaim = commentClaimRepository.findByCommentIdAndUserId(dto.getCommentId(), TokenUtil.getIdWithThrow());
        dto.setClaim(commentClaim.isPresent());
    }
}