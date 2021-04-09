package com.topcuoglu.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

public class User {

    private long id;
    private Date createdAt;
    private String username;
    private String name;
    private boolean isVerified;
    private String profileImageURL;
    private String description;
    private long followersCount;
    private long followingCount;
    private long tweetCount;
    private long listedCount;
    private boolean isProtected;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("createdAt")
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
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

    @JsonProperty("isVerified")
    public boolean isVerified() {
        return isVerified;
    }

    @JsonProperty("verified")
    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @JsonProperty("profileImageURL")
    public String getProfileImageURL() {
        return profileImageURL;
    }

    @JsonProperty("profile_image_url")
    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("followersCount")
    public long getFollowersCount() {
        return followersCount;
    }

    @JsonProperty("public_metrics")
    public void setFollowersCount(Map<String, Long> publicMetrics) {
        this.followersCount = publicMetrics.get("followers_count");
        this.followingCount = publicMetrics.get("following_count");
        this.tweetCount = publicMetrics.get("tweet_count");
        this.listedCount = publicMetrics.get("listed_count");
    }

    @JsonProperty("followingCount")
    public long getFollowingCount() {
        return followingCount;
    }

    @JsonProperty("tweetCount")
    public long getTweetCount() {
        return tweetCount;
    }

    @JsonProperty("listedCount")
    public long getListedCount() {
        return listedCount;
    }

    @JsonProperty("isProtected")
    public boolean isProtected() {
        return isProtected;
    }

    @JsonProperty("protected")
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
