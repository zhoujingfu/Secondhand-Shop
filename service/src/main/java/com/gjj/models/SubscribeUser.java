package com.gjj.models;

/**
 * Created by gjj on 2018-05-12.
 */
public class SubscribeUser {
    private Integer id;

    private String nickName;

    private String avatarUrl;

    private String gender;

    private Integer subscribe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    @Override
    public String toString() {
        return "SubscribeUser{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", subscribe=" + subscribe +
                '}';
    }
}
