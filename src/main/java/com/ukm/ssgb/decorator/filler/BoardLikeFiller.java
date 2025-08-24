package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.BoardLikeCode;
import com.ukm.ssgb.model.BoardLike;
import com.ukm.ssgb.repository.BoardLikeRepository;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardLikeFiller {

    private final BoardLikeRepository boardLikeRepository;

    public void fill(BoardLikeCode dto) {
        if (dto == null || Objects.isNull(TokenUtil.getIdWithNull())) {
            return;
        }

        Optional<BoardLike> like = boardLikeRepository.findByBoardIdAndUserId(dto.getBoardId(), TokenUtil.getIdWithThrow());
        dto.setLike(like.isPresent());
    }
}