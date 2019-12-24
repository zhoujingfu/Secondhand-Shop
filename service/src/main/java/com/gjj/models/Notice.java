package com.gjj.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gjj.constants.DateConstant;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    public String id;


    /**
     * 内容
     */
    @Column(name = "content", length = 50)
    public String content;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_date")
    @JsonFormat(pattern = DateConstant.DATE_TIME_FORMAT_PATTERN, timezone = "GMT+8")
    public Date addedDate;

    /**
     * 省略 get  set
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}
