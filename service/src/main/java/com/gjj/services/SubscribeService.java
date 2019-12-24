package com.gjj.services;

import com.gjj.models.Subscribe;
import com.gjj.repositories.SubscribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gjj on 2018-04-29.
 */
@Service
public class SubscribeService {
    @Autowired
    private SubscribeRepository subscribeRepository;

    public List<Subscribe> getSubscribe (Integer id, String nickName) {
        if (nickName == null) {
            nickName = "";
        }
        List<Subscribe> list = subscribeRepository.getSubscribe(id, nickName);
        return list;
    }
}
