package com.gjj.repositories;

import com.gjj.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by gjj on 2018-05-05.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>, QueryDslPredicateExecutor<Comment> {
}
