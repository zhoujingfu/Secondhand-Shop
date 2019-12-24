package com.gjj.qModels;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.gjj.models.Attachment;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachment is a Querydsl query type for Attachment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachment extends EntityPathBase<Attachment> {

    private static final long serialVersionUID = -812692397L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachment attachment = new QAttachment("attachment");

    public final StringPath attachmentName = createString("attachmentName");

    public final StringPath attachmentUrl = createString("attachmentUrl");

    public final QGoods goods;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QAttachment(String variable) {
        this(Attachment.class, forVariable(variable), INITS);
    }

    public QAttachment(Path<? extends Attachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachment(PathMetadata metadata, PathInits inits) {
        this(Attachment.class, metadata, inits);
    }

    public QAttachment(Class<? extends Attachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goods = inits.isInitialized("goods") ? new QGoods(forProperty("goods"), inits.get("goods")) : null;
    }

}

