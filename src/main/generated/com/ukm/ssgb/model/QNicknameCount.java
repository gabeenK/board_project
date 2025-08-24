package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNicknameCount is a Querydsl query type for NicknameCount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNicknameCount extends EntityPathBase<NicknameCount> {

    private static final long serialVersionUID = -1316104531L;

    public static final QNicknameCount nicknameCount = new QNicknameCount("nicknameCount");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final NumberPath<Long> modifiedBy = _super.modifiedBy;

    public final NumberPath<Long> nicknameCountId = createNumber("nicknameCountId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QNicknameCount(String variable) {
        super(NicknameCount.class, forVariable(variable));
    }

    public QNicknameCount(Path<? extends NicknameCount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNicknameCount(PathMetadata metadata) {
        super(NicknameCount.class, metadata);
    }

}

