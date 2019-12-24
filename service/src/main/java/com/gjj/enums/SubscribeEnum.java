package com.gjj.enums;

/**
 * Created by gjj on 2018-05-12.
 */
public enum SubscribeEnum {

    /*未关注*/
    UNSUBSCRIBE(0),
    /*关注*/
    ALREADYSUBSCRIBE(1);


    private Integer subscribe;

    SubscribeEnum(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getSubscribe() {
        return subscribe;
    }
}
