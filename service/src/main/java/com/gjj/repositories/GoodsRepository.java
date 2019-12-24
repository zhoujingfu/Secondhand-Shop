package com.gjj.repositories;

import com.gjj.models.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by gjj on 2018-03-09.
 */
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer>, QueryDslPredicateExecutor<Goods> {

}
