package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusinessType is a Querydsl query type for BusinessType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessType extends EntityPathBase<BusinessType> {

    private static final long serialVersionUID = 2110258222L;

    public static final QBusinessType businessType = new QBusinessType("businessType");

    public final NumberPath<Long> businessTypeId = createNumber("businessTypeId", Long.class);

    public final StringPath businessTypeName = createString("businessTypeName");

    public QBusinessType(String variable) {
        super(BusinessType.class, forVariable(variable));
    }

    public QBusinessType(Path<? extends BusinessType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusinessType(PathMetadata metadata) {
        super(BusinessType.class, metadata);
    }

}

