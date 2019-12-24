package com.gjj.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gjj.constants.DateConstant;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_user")
public class AdminUser {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id", length = 36)
    public String id;

    /**
     * 账号
     */
    @Column(name = "account", length = 50)
    public String account;

    /**
     * 手机号
     */
    @Column(name = "phone", length = 50)
    public String phone;

    /**
     * 姓名
     */
    @Column(name = "user_name", length = 50)
    public String name;

    /**
     * Email
     */
    @Column(name = "email", length = 50)
    public String email;

    /**
     * 加密后的密码
     */
    @Column(name = "password", length = 50)
    @JsonIgnore
    public String password;

    /**
     * 加密后加码
     */
    @Column(name = "salt", length = 4)
    @JsonIgnore
    public String salt;

    /**
     * 是否为管理员
     */
    @Column(name = "is_admin")
    @org.hibernate.annotations.Type(type = "yes_no")
    public Boolean admin;

    /**
     * 最后一次登录时间
     */
    @Column(name = "last_login_time")
    @JsonFormat(pattern = DateConstant.DATE_TIME_FORMAT_PATTERN, timezone = "GMT+8")
    public Date lastLoginTime;

    /**
     * 录入日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_date")
    @JsonFormat(pattern = DateConstant.DATE_TIME_FORMAT_PATTERN, timezone = "GMT+8")
    public Date addedDate;

}
