package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserMac is a Querydsl query type for UserMac
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserMac extends EntityPathBase<UserMac> {

    private static final long serialVersionUID = 1147876336L;

    public static final QUserMac userMac = new QUserMac("userMac");

    public final StringPath macAddress = createString("macAddress");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Long> userMacId = createNumber("userMacId", Long.class);

    public QUserMac(String variable) {
        super(UserMac.class, forVariable(variable));
    }

    public QUserMac(Path<? extends UserMac> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMac(PathMetadata metadata) {
        super(UserMac.class, metadata);
    }

}

