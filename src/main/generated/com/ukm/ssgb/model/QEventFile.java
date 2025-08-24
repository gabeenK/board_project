package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventFile is a Querydsl query type for EventFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventFile extends EntityPathBase<EventFile> {

    private static final long serialVersionUID = -988766558L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventFile eventFile = new QEventFile("eventFile");

    public final QEvent event;

    public final NumberPath<Long> eventFileId = createNumber("eventFileId", Long.class);

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public QEventFile(String variable) {
        this(EventFile.class, forVariable(variable), INITS);
    }

    public QEventFile(Path<? extends EventFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventFile(PathMetadata metadata, PathInits inits) {
        this(EventFile.class, metadata, inits);
    }

    public QEventFile(Class<? extends EventFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event")) : null;
    }

}

