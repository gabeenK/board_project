package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatFile is a Querydsl query type for ChatFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatFile extends EntityPathBase<ChatFile> {

    private static final long serialVersionUID = -1366862840L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatFile chatFile = new QChatFile("chatFile");

    public final QChat chat;

    public final NumberPath<Long> chatFileId = createNumber("chatFileId", Long.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public QChatFile(String variable) {
        this(ChatFile.class, forVariable(variable), INITS);
    }

    public QChatFile(Path<? extends ChatFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatFile(PathMetadata metadata, PathInits inits) {
        this(ChatFile.class, metadata, inits);
    }

    public QChatFile(Class<? extends ChatFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chat = inits.isInitialized("chat") ? new QChat(forProperty("chat")) : null;
    }

}

