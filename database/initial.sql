CREATE DATABASE IF NOT EXISTS hashtag_tracker;

USE hashtag_tracker;

CREATE TABLE IF NOT EXISTS user
(
    id                bigint unsigned primary key auto_increment,
    created_at        datetime     NOT NULL,
    username          varchar(100) NOT NULL,
    name              varchar(200) NOT NULL,
    is_verified       boolean      NOT NULL,
    profile_image_url varchar(150) NOT NULL,
    description       varchar(200) NOT NULL,
    followers_count   int unsigned NOT NULL,
    following_count   int unsigned NOT NULL,
    tweet_count       int unsigned NOT NULL,
    listed_count      int unsigned NOT NULL,
    is_protected      boolean      NOT NULL
);

CREATE TABLE IF NOT EXISTS tweet
(
    id         bigint unsigned primary key auto_increment,
    text       varchar(350)    NOT NULL,
    lang       varchar(5)      NOT NULL,
    created_at datetime        NOT NULL,
    author_id  bigint unsigned NOT NULL,
    FOREIGN KEY (author_id) REFERENCES user (id)
);