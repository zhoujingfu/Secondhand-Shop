package com.gjj.services;

import com.gjj.qModels.QGoods;
import com.gjj.repositories.GoodsRepository;
import com.gjj.repositories.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gjj on 2018-05-08.
 */
@Service
public class StatisticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    public Long registerCount() {
        Long registerUsers = userRepository.count();
        return registerUsers;
    }

    public Long goodsCount() {
        Long goodsCount = goodsRepository.count();
        return goodsCount;
    }

    public Long isDealGoodsCount() {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGoods qGoods = QGoods.goods;
        if (true) {
            booleanBuilder.and(qGoods.customerId.isNotNull());
        }
        Long count = goodsRepository.count(booleanBuilder);
        return count;
    }

    public Long getGoodsCountByType(String GoodsType) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGoods qGoods = QGoods.goods;
        if (GoodsType != null && !GoodsType.trim().isEmpty()) {
            booleanBuilder.and(qGoods.type.eq(GoodsType));
        }
        Long  count = goodsRepository.count(booleanBuilder);
        return count;
    }
}
