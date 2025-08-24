package com.ukm.ssgb.repository;

import com.ukm.ssgb.model.BoardBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardBookmarkRepository extends JpaRepository<BoardBookmark, Long> {

    @Query("select b from BoardBookmark b where b.board.boardId = ?1 and b.userId = ?2")
    Optional<BoardBookmark> findByBoardIdAndUserId(Long boardId, Long userId);

    Page<BoardBookmark> findAllByUserId(Long userId, Pageable pageable);
}
