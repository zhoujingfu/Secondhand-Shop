package com.gjj.repositories;

import com.gjj.models.Goods;
import com.gjj.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gjj on 2018-03-04
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>, QueryDslPredicateExecutor<User> {
    User findByUsername(String username);
    List<User> findAll();
    User findByOpenid(String openid);

    @Query(value="select u.id from user u where u.openid = ?1", nativeQuery = true)
    Integer getUserIdByOpenid (String openid);

    @Query(value="select u.username from user u where u.openid = ?1", nativeQuery = true)
    String  getUsernameByOpenid (String openid);

    @Query(value="select u.role from user u where u.openid = ?1", nativeQuery = true)
    Integer getUserRoleByOpenid (String openid);

    @Query(value="select u.state from user u where u.openid = ?1", nativeQuery = true)
    Integer getUserStateByOpenid (String openid);


}
