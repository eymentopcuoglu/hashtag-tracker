package com.topcuoglu.springbootconsumer.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "profile_image_url")
    private String profileImageURL;

    @Column(name = "description")
    private String description;

    @Column(name = "followers_count")
    private long followersCount;

    @Column(name = "following_count")
    private long followingCount;

    @Column(name = "tweet_count")
    private long tweetCount;

    @Column(name = "listed_count")
    private long listedCount;

    @Column(name = "is_protected")
    private boolean isProtected;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }

    public long getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(long tweetCount) {
        this.tweetCount = tweetCount;
    }

    public long getListedCount() {
        return listedCount;
    }

    public void setListedCount(long listedCount) {
        this.listedCount = listedCount;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", isVerified=" + isVerified +
                ", profileImageURL='" + profileImageURL + '\'' +
                ", description='" + description + '\'' +
                ", followersCount=" + followersCount +
                ", followingCount=" + followingCount +
                ", tweetCount=" + tweetCount +
                ", listedCount=" + listedCount +
                ", isProtected=" + isProtected +
                '}';
    }
}
