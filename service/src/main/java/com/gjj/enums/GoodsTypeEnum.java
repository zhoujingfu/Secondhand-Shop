package com.gjj.enums;

/**
 * Created by gjj on 2018-03-13.
 */
public enum GoodsTypeEnum {
//    digital product
    /**闲置数码*/
    DIGITAL_PRODUCT("闲置数码"),
    /**家具日用*/
    FURNITURE_COMMODITY("家具日用"),
    /**图书音像*/
    BOOK("图书音像"),
    /**鞋服配饰*/
    SHOES_CLOTHING_ACCESSORIES("鞋服配饰"),
    /**美妆洗护*/
    COSMETICS("美妆洗护"),
    /**文体户外*/
    CULTURE_SPORTS_OUTDOORS("文体户外"),
    /**办公用品*/
    OFFICE_SUPPLIES("办公用品"),
    /**其他*/
    OTHERS("其他");

    private String code;
    GoodsTypeEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
