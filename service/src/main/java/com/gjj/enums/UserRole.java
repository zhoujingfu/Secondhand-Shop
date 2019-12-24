package com.gjj.enums;

/**
 * Created by gjj on 2018-03-30.
 */
public enum UserRole {
    /*普通用户*/
    ORDINARY(0),
    /*管理员*/
    ADMINISTRATORS(1);

    private Integer role;
    UserRole(Integer role) {
        this.role = role;
    }
    public Integer getRole() {
        return role;
    }
}
