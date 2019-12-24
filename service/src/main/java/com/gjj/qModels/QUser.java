package com.gjj.qModels;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.gjj.models.StudentId;
import com.gjj.models.Goods;
import com.gjj.models.User;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -976157957L;

    public static final QUser user = new QUser("user");

    public final StringPath avatarUrl = createString("avatarUrl");

    //public final ListPath<StudentId, QStudentId> studentId = this.<StudentId, QStudentId>createList("studentId", StudentId.class, QStudentId.class, PathInits.DIRECT2);

    public final StringPath gender = createString("gender");

    public final SetPath<Goods, QGoods> goods = this.<Goods, QGoods>createSet("goods", Goods.class, QGoods.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath mobile = createString("mobile");

    public final StringPath nickName = createString("nickName");

    public final StringPath openid = createString("openid");

    public final StringPath password = createString("password");

    public final StringPath qq = createString("qq");

    public final NumberPath<Integer> role = createNumber("role", Integer.class);

    public final NumberPath<Integer> state = createNumber("state", Integer.class);

    public final StringPath username = createString("username");

    public final SetPath<User, QUser> users = this.<User, QUser>createSet("users", User.class, QUser.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

