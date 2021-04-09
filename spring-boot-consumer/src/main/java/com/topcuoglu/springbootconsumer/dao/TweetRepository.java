package com.topcuoglu.springbootconsumer.dao;

import com.topcuoglu.springbootconsumer.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}