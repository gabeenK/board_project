package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatUser is a Querydsl query type for ChatUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatUser extends EntityPathBase<ChatUser> {

    private static final long serialVersionUID = -1366406569L;

    public static final QChatUser chatUser = new QChatUser("chatUser");

    public final QBaseCreatedEntity _super = new QBaseCreatedEntity(this);

    public final NumberPath<Long> chatRoomId = createNumber("chatRoomId", Long.class);

    public final NumberPath<Long> chatRoomUserId = createNumber("chatRoomUserId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QChatUser(String variable) {
        super(ChatUser.class, forVariable(variable));
    }

    public QChatUser(Path<? extends ChatUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatUser(PathMetadata metadata) {
        super(ChatUser.class, metadata);
    }

}

