package com.gjj.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gjj on 2018-03-04
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","goods","users"})
public class User extends SubscribeUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @JsonIgnore
    @Column(name = "openid")
    private String openid;

    @Column(name = "username", unique = true, length = 50)
    @JsonProperty("username")
    private String username;

    @Column(name = "nick_name")
    @JsonProperty("nickName")
    private String nickName;

    @Column(name = "password", length = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "state", length = 10)
    @JsonProperty("state")
    private Integer state;

    @Column(name = "role", length = 10)
    @JsonProperty("role")
    private Integer role;

    @Column(name = "mobile", length = 50)
    @JsonProperty("mobile")
    private String mobile;

    @Column(name = "qq", length = 50)
    @JsonProperty("qq")
    private String qq;

    @Column(name = "avatar_url", length = 200)
    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @Column(name = "gender", length = 10)
    @JsonProperty("gender")
    private String gender;

//    @JsonIgnore
// @JsonProperty("user_goods")
    @JsonBackReference(value = "b")
    @OneToMany(targetEntity = Goods.class,fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Goods> goods = new HashSet<>();

    @ManyToMany(targetEntity = User.class)
    @JsonBackReference(value = "a")
    @JoinTable(name = "user_relation", joinColumns = @JoinColumn(name = "active_id"), inverseJoinColumns = @JoinColumn(name = "passive_id"))
    private Set<User> users = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", username='" + username + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", role=" + role +
                ", mobile='" + mobile + '\'' +
                ", qq='" + qq + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender='" + gender + '\'' +
//                ", goods=" + goods +
//                ", users=" + users +
                '}';
    }
}
