package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = 41156716L;

    public static final QChat chat = new QChat("chat");

    public final QBaseCreatedEntity _super = new QBaseCreatedEntity(this);

    public final ListPath<ChatFile, QChatFile> chatFiles = this.<ChatFile, QChatFile>createList("chatFiles", ChatFile.class, QChatFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> chatId = createNumber("chatId", Long.class);

    public final NumberPath<Long> chatRoomId = createNumber("chatRoomId", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final BooleanPath hasAttachments = createBoolean("hasAttachments");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QChat(String variable) {
        super(Chat.class, forVariable(variable));
    }

    public QChat(Path<? extends Chat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChat(PathMetadata metadata) {
        super(Chat.class, metadata);
    }

}

