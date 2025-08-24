package com.ukm.ssgb.repository.impl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ukm.ssgb.dto.board.AdminBoardListDto;
import com.ukm.ssgb.dto.board.AdminBoardsRequestDto;
import com.ukm.ssgb.repository.CustomBoardRepository;
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
import static com.ukm.ssgb.dto.board.AdminBoardsRequestDto.BoardSort;
import static com.ukm.ssgb.model.QBoard.board;

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AdminBoardListDto> findAllByAdmin(AdminBoardsRequestDto requestDto) {
        Pageable pageable = requestDto.getPageRequest();
        BoardType boardType = requestDto.getBoardType();
        String query = requestDto.getQuery();
        Boolean hidden = requestDto.getHidden();
        Boolean deleted = requestDto.getDeleted();

        List<AdminBoardListDto> content = jpaQueryFactory.select(Projections.constructor(AdminBoardListDto.class, board))
                .from(board)
                .where(
                        boardTypeEq(boardType),
                        queryContains(query),
                        hiddenEq(hidden),
                        deletedEq(deleted)
                )
                .orderBy(createOrderSpecifier(requestDto.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(board.count())
                .from(board)
                .where(
                        boardTypeEq(boardType),
                        queryContains(query),
                        hiddenEq(hidden),
                        deletedEq(deleted)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression nicknameContains(String query) {
        return query == null ? null : board.nickname.contains(query);
    }

    private Predicate queryContains(String query) {
        return anyOf(
                titleContains(query),
                contentContains(query),
                nicknameContains(query));
    }

    private BooleanExpression titleContains(String query) {
        return query == null ? null : board.title.contains(query);
    }

    private BooleanExpression contentContains(String query) {
        return query == null ? null : board.content.contains(query);
    }

    private BooleanExpression boardTypeEq(BoardType boardType) {
        return boardType == null ? null : board.boardType.eq(boardType);
    }

    private BooleanExpression hiddenEq(Boolean hidden) {
        return hidden == null ? null : hidden ? board.claimCount.goe(CLAIM_MAX_COUNT) : board.claimCount.lt(CLAIM_MAX_COUNT);
    }

    private BooleanExpression deletedEq(Boolean deleted) {
        return deleted == null ? null : deleted ? board.deletedType.ne(DeletedType.NONE) : board.deletedType.eq(DeletedType.NONE);
    }

    private OrderSpecifier<?> createOrderSpecifier(BoardSort sort) {
        return switch (sort) {
            case view -> new OrderSpecifier<>(Order.DESC, board.viewCount);
            case like -> new OrderSpecifier<>(Order.DESC, board.likeCount);
            default -> new OrderSpecifier<>(Order.DESC, board.createdAt);
        };
    }
}
