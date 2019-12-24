package com.gjj.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by gjj on 2018-03-08.
 */
@Entity
@Table(name = "studentid")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","user"})
public class StudentId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @Column(name = "attachment_name", length = 200)
    @JsonProperty("attachmentName")
    private String attachmentName;

    @NotNull
    @Column(name = "attachment_url", length = 200)
    @JsonProperty("attachmentUrl")
    private String attachmentUrl;

    @NotNull
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id",insertable = false, updatable = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "StudentId{" +
                "id=" + id +
                ", attachmentName='" + attachmentName + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
//                ", user=" + user +
                '}';
    }
}
