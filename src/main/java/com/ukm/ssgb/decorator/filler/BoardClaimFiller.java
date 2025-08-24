package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.BoardClaimCode;
import com.ukm.ssgb.model.BoardClaim;
import com.ukm.ssgb.repository.BoardClaimRepository;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardClaimFiller {

    private final BoardClaimRepository boardClaimRepository;

    public void fill(BoardClaimCode dto) {
        if (dto == null || Objects.isNull(TokenUtil.getIdWithNull())) {
            return;
        }

        Optional<BoardClaim> boardClaim = boardClaimRepository.findByBoardIdAndUserId(dto.getBoardId(), TokenUtil.getIdWithThrow());
        dto.setClaim(boardClaim.isPresent());
    }
}