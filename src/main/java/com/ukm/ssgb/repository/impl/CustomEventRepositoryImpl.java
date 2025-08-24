package com.ukm.ssgb.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ukm.ssgb.dto.event.EventListByUserDto;
import com.ukm.ssgb.dto.event.EventListDto;
import com.ukm.ssgb.repository.CustomEventRepository;
import com.ukm.ssgb.type.ScreenType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.ukm.ssgb.model.QEvent.event;

@Repository
@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<EventListDto> findAllByActive(Boolean active, Pageable pageable) {
        List<EventListDto> content = jpaQueryFactory
                .select(Projections.constructor(EventListDto.class, event))
                .from(event)
                .where(
                        approvedEq(active),
                        deletedFalse()
                )
                .orderBy(event.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(event.count())
                .from(event)
                .where(
                        approvedEq(active),
                        deletedFalse()
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<EventListByUserDto> findActiveEventByScreenType(ScreenType screenType) {

        return jpaQueryFactory
                .select(Projections.constructor(EventListByUserDto.class, event))
                .from(event)
                .where(
                        screenTypeEq(screenType),
                        activeToday(),
                        activeTrue(),
                        deletedFalse()
                )
                .orderBy(event.createdAt.desc())
                .fetch();
    }

    private BooleanExpression approvedEq(Boolean active) {
        return active == null ? null : event.active.eq(active);
    }

    private BooleanExpression deletedFalse() {
        return event.deleted.isFalse();
    }

    private BooleanExpression screenTypeEq(ScreenType screenType) {
        return event.screenType.eq(screenType);
    }

    private BooleanExpression activeTrue() {
        return event.active.isTrue();
    }

    private BooleanExpression activeToday() {
        LocalDateTime now = LocalDateTime.now();
        return event.startAt.before(now).and(event.endAt.after(now));
    }
}
