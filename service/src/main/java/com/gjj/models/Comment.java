package com.gjj.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


/**
 * Created by gjj on 2018-03-20.
 */
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @Column(name = "content", length = 500)
    @JsonProperty("content")
    private String content;

    @NotNull
    @JsonProperty("goodsId")
    @JoinColumn(name = "goods_id")
    private Integer goodsId;


    @ManyToOne(targetEntity = User.class)
    @JsonProperty(value = "commentUser")
    @JoinColumn(name = "comment_user_id")
    private User user;

    @Column(name = "comment_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "commentDate")
    private Date commentDate;

    @OneToOne(targetEntity = User.class)
    @JsonProperty(value = "replyUser")
    @JoinColumn(name="reply_id")
    private User replyUser;

    @JsonProperty(value = "replyCommentId")
    @Column(name = "reply_comment_id")
    private Integer replyCommentId;

    @JsonProperty(value = "read")
    @Column(name = "comment_read")
    private Integer read;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public User getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(User replyUser) {
        this.replyUser = replyUser;
    }

    public Integer getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(Integer replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", goodsId=" + goodsId +
                ", user=" + user +
                ", commentDate=" + commentDate +
                ", replyUser=" + replyUser +
                ", replyCommentId=" + replyCommentId +
                ", read=" + read +
                '}';
    }
}
