package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentClaim is a Querydsl query type for CommentClaim
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentClaim extends EntityPathBase<CommentClaim> {

    private static final long serialVersionUID = -742455759L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentClaim commentClaim = new QCommentClaim("commentClaim");

    public final QBaseCreatedEntity _super = new QBaseCreatedEntity(this);

    public final QComment comment;

    public final NumberPath<Long> commentClaimId = createNumber("commentClaimId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCommentClaim(String variable) {
        this(CommentClaim.class, forVariable(variable), INITS);
    }

    public QCommentClaim(Path<? extends CommentClaim> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentClaim(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentClaim(PathMetadata metadata, PathInits inits) {
        this(CommentClaim.class, metadata, inits);
    }

    public QCommentClaim(Class<? extends CommentClaim> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
    }

}

