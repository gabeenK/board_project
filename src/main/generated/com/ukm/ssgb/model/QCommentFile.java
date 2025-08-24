package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentFile is a Querydsl query type for CommentFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentFile extends EntityPathBase<CommentFile> {

    private static final long serialVersionUID = 1361609959L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentFile commentFile = new QCommentFile("commentFile");

    public final QComment comment;

    public final NumberPath<Long> commentFileId = createNumber("commentFileId", Long.class);

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public QCommentFile(String variable) {
        this(CommentFile.class, forVariable(variable), INITS);
    }

    public QCommentFile(Path<? extends CommentFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentFile(PathMetadata metadata, PathInits inits) {
        this(CommentFile.class, metadata, inits);
    }

    public QCommentFile(Class<? extends CommentFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
    }

}

