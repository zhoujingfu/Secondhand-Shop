package com.gjj.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gjj on 2018-05-02.
 */
@Entity
@Table(name = "subscribe")
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @Column(name = "nick_name")
    @JsonProperty("nickName")
    private String nickName;

    @Column(name = "avatar_url", length = 200)
    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @Column(name = "gender", length = 10)
    @JsonProperty("gender")
    private String gender;

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

    @Override
    public String toString() {
        return "Subscribe{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
