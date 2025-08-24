package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.BoardClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardClaimRepository extends JpaRepository<BoardClaim, Long> {

    @Query("select b from BoardClaim b where b.board.boardId = ?1 and b.userId = ?2")
    Optional<BoardClaim> findByBoardIdAndUserId(Long boardId, Long userId);
}
