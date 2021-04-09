package com.topcuoglu.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Tweet {

    private long id;
    private String text;
    private String lang;
    private Date createdAt;
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

    @JsonProperty("createdAt")
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("authorId")
    public long getAuthorId() {
        return authorId;
    }

    @JsonProperty("author_id")
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
