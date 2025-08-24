package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1275143250L;

    public static final QBoard board = new QBoard("board");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<BoardBookmark, QBoardBookmark> boardBookmarks = this.<BoardBookmark, QBoardBookmark>createList("boardBookmarks", BoardBookmark.class, QBoardBookmark.class, PathInits.DIRECT2);

    public final ListPath<BoardClaim, QBoardClaim> boardClaim = this.<BoardClaim, QBoardClaim>createList("boardClaim", BoardClaim.class, QBoardClaim.class, PathInits.DIRECT2);

    public final ListPath<BoardFile, QBoardFile> boardFiles = this.<BoardFile, QBoardFile>createList("boardFiles", BoardFile.class, QBoardFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final ListPath<BoardLike, QBoardLike> boardLikes = this.<BoardLike, QBoardLike>createList("boardLikes", BoardLike.class, QBoardLike.class, PathInits.DIRECT2);

    public final EnumPath<com.ukm.ssgb.type.BoardType> boardType = createEnum("boardType", com.ukm.ssgb.type.BoardType.class);

    public final NumberPath<Long> claimCount = createNumber("claimCount", Long.class);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final EnumPath<com.ukm.ssgb.type.DeletedType> deletedType = createEnum("deletedType", com.ukm.ssgb.type.DeletedType.class);

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final NumberPath<Long> modifiedBy = _super.modifiedBy;

    public final StringPath nickname = createString("nickname");

    public final BooleanPath pin = createBoolean("pin");

    public final NumberPath<Long> profileImageId = createNumber("profileImageId", Long.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

