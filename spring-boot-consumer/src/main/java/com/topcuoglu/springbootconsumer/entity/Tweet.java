package com.topcuoglu.springbootconsumer.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tweet")
public class Tweet {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "lang")
    private String lang;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "author_id")
    private long authorId;

    public Tweet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", lang='" + lang + '\'' +
                ", createdAt=" + createdAt +
                ", authorId=" + authorId +
                '}';
    }
}
