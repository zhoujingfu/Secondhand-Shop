package com.gjj.qModels;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.gjj.models.Subscribe;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubscribe is a Querydsl query type for Subscribe
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubscribe extends EntityPathBase<Subscribe> {

    private static final long serialVersionUID = 1521796122L;

    public static final QSubscribe subscribe = new QSubscribe("subscribe");

    public final StringPath avatarUrl = createString("avatarUrl");

    public final StringPath gender = createString("gender");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath nickName = createString("nickName");

    public QSubscribe(String variable) {
        super(Subscribe.class, forVariable(variable));
    }

    public QSubscribe(Path<? extends Subscribe> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubscribe(PathMetadata metadata) {
        super(Subscribe.class, metadata);
    }

}

