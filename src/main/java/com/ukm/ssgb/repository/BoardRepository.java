package com.ukm.ssgb.repository;

import com.ukm.ssgb.dto.myPage.MyBoardListDto;
import com.ukm.ssgb.model.Board;
import com.ukm.ssgb.type.BoardType;
import com.ukm.ssgb.type.ContentType;
import com.ukm.ssgb.type.DeletedType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    Page<Board> findAllByBoardType(BoardType boardType, Pageable pageable);

    Optional<Board> findByBoardIdAndUserId(Long boardId, Long userId);

    List<Board> findByCreatedAtBetweenOrPinIsTrue(LocalDateTime begin, LocalDateTime end);

    int countByPinIsTrue();

    @Query(value = """
                    (
                     SELECT
                     	b.boardId,
                     	'BOARD' AS contentType,
                     	b.boardType,
                     	b.title,
                     	b.content,
                     	b.nickname,
                     	b.createdAt,
                     	b.deletedType
                     FROM
                     	board b
                     WHERE
                     	b.userId = :userId)
                     UNION ALL
                     (
                     SELECT
                     	c.boardId,
                     	'COMMENT' AS contentType,
                     	(select b1.boardType from board b1 where b1.boardId = c.boardId limit 0, 1) AS boardType,
                     	(select b1.title from board b1 where b1.boardId = c.boardId limit 0, 1) AS title,
                     	c.content,
                     	c.nickname,
                     	c.createdAt,
                        c.deletedType
                     FROM
                     	comment c
                     WHERE
                     	c.userId = :userId)
                     ORDER BY
                     createdAt DESC
            """,
            countQuery = """
                    SELECT
                      (SELECT COUNT(*) FROM board b WHERE b.userId = :userId) +
                      (SELECT COUNT(*) FROM comment c WHERE c.userId = :userId)
                    """,
            nativeQuery = true)
    Page<Object[]> findAllBoardAndCommentByUserId(Long userId, Pageable pageable);

    default Page<MyBoardListDto> findMappedAllBoardAndCommentByUserId(Long userId, Pageable pageable) {
        Page<Object[]> results = findAllBoardAndCommentByUserId(userId, pageable);

        List<MyBoardListDto> mappedList = results.stream()
                .map(result -> MyBoardListDto.builder()
                        .boardId((Long) result[0])
                        .contentType(ContentType.valueOf((String) result[1]))
                        .boardType(BoardType.valueOf((String) result[2]))
                        .title((String) result[3])
                        .content((String) result[4])
                        .nickname((String) result[5])
                        .createdAt(((Timestamp) result[6]).toLocalDateTime())
                        .deletedType(DeletedType.valueOf((String) result[7]))
                        .build()
                )
                .collect(Collectors.toList());

        return new PageImpl<>(mappedList, pageable, results.getTotalElements());
    }
}
