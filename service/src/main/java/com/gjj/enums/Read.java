package com.gjj.enums;

/**
 * Created by gjj on 2018-05-09.
 */
public enum Read {
    /*未读*/
    UNREAD(0),
    /*已读*/
    ALREADYREAD(1);


    private Integer read;

    Read(Integer read) {
        this.read = read;
    }

    public Integer getRead() {
        return read;
    }

}
