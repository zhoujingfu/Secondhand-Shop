package com.gjj.qModels;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.gjj.models.Attachment;
import com.gjj.models.Goods;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoods is a Querydsl query type for Goods
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGoods extends EntityPathBase<Goods> {

    private static final long serialVersionUID = -209164762L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoods goods = new QGoods("goods");

    public final ListPath<Attachment, QAttachment> attachments = this.<Attachment, QAttachment>createList("attachments", Attachment.class, QAttachment.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> bulletinDate = createDateTime("bulletinDate", java.util.Date.class);

    public final NumberPath<Integer> customerId = createNumber("customerId", Integer.class);

    public final StringPath goodsName = createString("goodsName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Double> originalPrice = createNumber("originalPrice", Double.class);

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final StringPath spec = createString("spec");

    public final StringPath type = createString("type");

    public final QUser user;

    public QGoods(String variable) {
        this(Goods.class, forVariable(variable), INITS);
    }

    public QGoods(Path<? extends Goods> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoods(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoods(PathMetadata metadata, PathInits inits) {
        this(Goods.class, metadata, inits);
    }

    public QGoods(Class<? extends Goods> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

