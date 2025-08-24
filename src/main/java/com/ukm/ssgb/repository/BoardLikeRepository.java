package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    @Query("select b from BoardLike b where b.board.boardId = ?1 and b.userId = ?2")
    Optional<BoardLike> findByBoardIdAndUserId(Long boardId, Long userId);
}
