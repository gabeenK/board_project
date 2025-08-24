package com.ukm.ssgb.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ukm.ssgb.dto.usermanagement.GetUsersDto;
import com.ukm.ssgb.repository.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.anyOf;
import static com.ukm.ssgb.model.QBusinessType.businessType;
import static com.ukm.ssgb.model.QRegion.region;
import static com.ukm.ssgb.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<GetUsersDto> findAllByQueryAndApproved(String query, Boolean approved, Pageable pageable) {
        List<GetUsersDto> content = jpaQueryFactory
                .select(Projections.constructor(GetUsersDto.class, user, businessType.businessTypeName, region.regionName))
                .from(user)
                .leftJoin(businessType)
                .on(user.businessTypeId.eq(businessType.businessTypeId))
                .leftJoin(region)
                .on(user.regionId.eq(region.regionId))
                .where(
                        queryContains(query),
                        approvedEq(approved),
                        deletedFalse())
                .orderBy(user.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(user.count())
                .from(user)
                .leftJoin(businessType)
                .on(user.businessTypeId.eq(businessType.businessTypeId))
                .leftJoin(region)
                .on(user.regionId.eq(region.regionId))
                .where(
                        queryContains(query),
                        approvedEq(approved),
                        deletedFalse());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Predicate queryContains(String query) {
        return anyOf(
                emailContains(query),
                businessTypeNameContains(query),
                regionNameContains(query));
    }

    private BooleanExpression emailContains(String query) {
        return query == null ? null : user.email.contains(query);
    }

    private BooleanExpression businessTypeNameContains(String query) {
        return query == null ? null : businessType.businessTypeName.contains(query);
    }

    private BooleanExpression regionNameContains(String query) {
        return query == null ? null : region.regionName.contains(query);
    }

    private BooleanExpression approvedEq(Boolean approved) {
        return approved == null ? null : user.approved.eq(approved);
    }

    private BooleanExpression deletedFalse() {
        return user.deleted.isFalse();
    }
}
