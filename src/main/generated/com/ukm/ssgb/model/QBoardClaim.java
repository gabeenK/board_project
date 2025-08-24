package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardClaim is a Querydsl query type for BoardClaim
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardClaim extends EntityPathBase<BoardClaim> {

    private static final long serialVersionUID = 182810378L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardClaim boardClaim = new QBoardClaim("boardClaim");

    public final QBaseCreatedEntity _super = new QBaseCreatedEntity(this);

    public final QBoard board;

    public final NumberPath<Long> boardClaimId = createNumber("boardClaimId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBoardClaim(String variable) {
        this(BoardClaim.class, forVariable(variable), INITS);
    }

    public QBoardClaim(Path<? extends BoardClaim> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardClaim(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardClaim(PathMetadata metadata, PathInits inits) {
        this(BoardClaim.class, metadata, inits);
    }

    public QBoardClaim(Class<? extends BoardClaim> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
    }

}

