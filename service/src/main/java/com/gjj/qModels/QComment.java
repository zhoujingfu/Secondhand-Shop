package com.gjj.qModels;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.gjj.models.Comment;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 1599490351L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final DateTimePath<java.util.Date> commentDate = createDateTime("commentDate", java.util.Date.class);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> goodsId = createNumber("goodsId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> read = createNumber("read", Integer.class);

    public final NumberPath<Integer> replyCommentId = createNumber("replyCommentId", Integer.class);

    public final QUser replyUser;

    public final QUser user;

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.replyUser = inits.isInitialized("replyUser") ? new QUser(forProperty("replyUser")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

