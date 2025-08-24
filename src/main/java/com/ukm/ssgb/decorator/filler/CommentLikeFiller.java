package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.CommentLikeCode;
import com.ukm.ssgb.model.CommentLike;
import com.ukm.ssgb.repository.CommentLikeRepository;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentLikeFiller {

    private final CommentLikeRepository commentLikeRepository;

    public void fill(CommentLikeCode dto) {
        if (dto == null || Objects.isNull(TokenUtil.getIdWithNull())) {
            return;
        }

        Optional<CommentLike> like = commentLikeRepository.findByCommentIdAndUserId(dto.getCommentId(), TokenUtil.getIdWithThrow());
        dto.setLike(like.isPresent());
    }
}