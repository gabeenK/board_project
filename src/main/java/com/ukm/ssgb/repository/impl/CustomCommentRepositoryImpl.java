package com.ukm.ssgb.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ukm.ssgb.dto.comment.AdminCommentListDto;
import com.ukm.ssgb.dto.comment.AdminCommentsRequestDto;
import com.ukm.ssgb.repository.CustomCommentRepository;
import com.ukm.ssgb.type.BoardType;
import com.ukm.ssgb.type.DeletedType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.anyOf;
import static com.ukm.ssgb.constant.ServiceConstants.CLAIM_MAX_COUNT;
import static com.ukm.ssgb.model.QBoard.board;
import static com.ukm.ssgb.model.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AdminCommentListDto> findAllByAdmin(AdminCommentsRequestDto requestDto) {
        Pageable pageable = requestDto.getPageRequest();
        BoardType boardType = requestDto.getBoardType();
        String query = requestDto.getQuery();
        Boolean hidden = requestDto.getHidden();
        Boolean deleted = requestDto.getDeleted();

        List<AdminCommentListDto> content = jpaQueryFactory.select(Projections.constructor(AdminCommentListDto.class, comment, board))
                .from(comment)
                .leftJoin(comment.board)
                .where(
                        comment.parentCommentId.isNull(),
                        boardTypeEq(boardType),
                        queryContains(query),
                        hiddenEq(hidden),
                        deletedEq(deleted)
                )
                .orderBy(comment.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .leftJoin(comment.board)
                .where(
                        comment.parentCommentId.isNull(),
                        boardTypeEq(boardType),
                        queryContains(query),
                        hiddenEq(hidden),
                        deletedEq(deleted)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<AdminCommentListDto> findAllChildCommentByAdmin(List<Long> parentCommentIds) {
        return jpaQueryFactory.select(Projections.constructor(AdminCommentListDto.class, comment, board))
                .from(comment)
                .leftJoin(comment.board)
                .where(comment.parentCommentId.in(parentCommentIds))
                .fetch();
    }

    private Predicate queryContains(String query) {
        return anyOf(
                contentContains(query),
                nicknameContains(query));
    }

    private BooleanExpression contentContains(String query) {
        return query == null ? null : comment.content.contains(query);
    }

    private BooleanExpression nicknameContains(String query) {
        return query == null ? null : comment.nickname.contains(query);
    }

    private BooleanExpression boardTypeEq(BoardType boardType) {
        return boardType == null ? null : board.boardType.eq(boardType);
    }

    private BooleanExpression hiddenEq(Boolean hidden) {
        return hidden == null ? null : hidden ? comment.claimCount.goe(CLAIM_MAX_COUNT) : comment.claimCount.lt(CLAIM_MAX_COUNT);
    }

    private BooleanExpression deletedEq(Boolean deleted) {
        return deleted == null ? null : deleted ? comment.deletedType.ne(DeletedType.NONE) : comment.deletedType.eq(DeletedType.NONE);
    }
}
