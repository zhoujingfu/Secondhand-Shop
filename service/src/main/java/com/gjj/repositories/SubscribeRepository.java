package com.gjj.repositories;

import com.gjj.models.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gjj on 2018-04-29.
 */
@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe,Integer> {

    @Query(value="SELECT user_relation.passive_id as id ,user.nick_name,`user`.avatar_url,`user`.gender,`user`.gender FROM " +
            "user_relation LEFT JOIN `user` ON user_relation.passive_id = `user`.id WHERE user_relation.active_id = :id and `user`.nick_name LIKE %:nickName% ", nativeQuery = true)
    List<Subscribe> getSubscribe (@Param("id") Integer id,
                                  @Param("nickName") String nickName);



}
