package com.gjj.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.exceptions.BusinessException;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.Goods;
import com.gjj.models.User;
import com.gjj.qModels.QGoods;
import com.gjj.repositories.GoodsRepository;
import com.gjj.repositories.UserRepository;
import com.gjj.utils.IgnoreProperty;
import com.gjj.utils.ObjectMapperBuilder;
import com.querydsl.core.BooleanBuilder;
import javassist.CannotCompileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created by gjj on 2018-03-09 .
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    public Goods getGoodsById(Integer id) {
        Goods goods;
//        try {
            goods = goodsRepository.findOne(id);
            if (goods == null) {
                throw new UnAuthorizedException(ErrorCode.NOT_FOUND_GOODS, ErrorMessage.NOT_FOUND_GOODS);
            }
//        } catch (BusinessException e) {
//            throw new UnAuthorizedException(ErrorCode.NOT_FOUND_GOODS, ErrorMessage.NOT_FOUND_GOODS);
//        }
        return goods;
    }

    @IgnoreProperty(pojo = User.class, value = {"user_goods"})
    public Page<Map<String, Object>> getGoods (Integer id, String goodsName, String type, Integer customerId, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGoods qGoods = QGoods.goods;
        if (id != null) {
            booleanBuilder.and(qGoods.user.id.eq(id));
        }
        if (goodsName != null && !goodsName.trim().isEmpty()) {
            booleanBuilder.and(qGoods.goodsName.contains(goodsName));
        }
        if (type !=null && !type.trim().isEmpty()) {
            booleanBuilder.and(qGoods.type.eq(type.trim()));
        }

        if (customerId != null) {
            booleanBuilder.and(qGoods.customerId.eq(customerId));
        } else {
            booleanBuilder.and(qGoods.customerId.isNull());
        }
        ObjectMapper objectMapper;
        Page<Goods> goods = goodsRepository.findAll(booleanBuilder,pageable);
        try {
            objectMapper = new ObjectMapperBuilder().build(GoodsService.class.getMethod("getGoods",Integer.class,String.class,String.class,Integer.class,Pageable.class));
            return new ObjectMapperBuilder().mapPagedObjects(goods,objectMapper);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @IgnoreProperty(pojo = User.class, value = {"user_goods"})
    public Goods saveGoods(Goods goods) {
//        ObjectMapper objectMapper;
        Goods goods1 = goodsRepository.save(goods);
//        try {
//            objectMapper = new ObjectMapperBuilder().build(GoodsService.class.getMethod("saveGoods",Goods.class));
//            return new ObjectMapperBuilder().mapObjects(goods1,objectMapper);
//        }  catch (Exception e) {
//            e.printStackTrace();
//        }
        return goods1;
    }

    public void deleteGoods(Integer id) {
        goodsRepository.delete(id);
    }

    /**
     * 获取某人的所有商品
     */
    public List<Goods> getAllGoodsByUser(Integer id, String goodsName, String type) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGoods qGoods = QGoods.goods;
        if (id != null) {
            booleanBuilder.and(qGoods.user.id.eq(id));
        }
        if (goodsName != null && !goodsName.trim().isEmpty()) {
            booleanBuilder.and(qGoods.goodsName.contains(goodsName));
        }
        if (type !=null && !type.trim().isEmpty()) {
            booleanBuilder.and(qGoods.type.eq(type.trim()));
        }
        List<Goods> list = (List<Goods>)goodsRepository.findAll(booleanBuilder);
        return list;
    }
}
