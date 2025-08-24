package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNickname is a Querydsl query type for Nickname
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNickname extends EntityPathBase<Nickname> {

    private static final long serialVersionUID = 1561815938L;

    public static final QNickname nickname = new QNickname("nickname");

    public final QBaseCreatedEntity _super = new QBaseCreatedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath adjective = createString("adjective");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final NumberPath<Long> nicknameId = createNumber("nicknameId", Long.class);

    public final StringPath noun = createString("noun");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QNickname(String variable) {
        super(Nickname.class, forVariable(variable));
    }

    public QNickname(Path<? extends Nickname> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNickname(PathMetadata metadata) {
        super(Nickname.class, metadata);
    }

}

