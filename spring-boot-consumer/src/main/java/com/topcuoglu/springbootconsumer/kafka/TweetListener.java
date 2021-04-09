package com.topcuoglu.springbootconsumer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topcuoglu.springbootconsumer.service.TweetService;
import com.topcuoglu.springbootconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.topcuoglu.springbootconsumer.entity.Tweet;
import com.topcuoglu.springbootconsumer.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TweetListener {

    private final ObjectMapper objectMapper;
    private final TweetService tweetService;
    private final UserService userService;

    @Autowired
    public TweetListener(ObjectMapper objectMapper, TweetService tweetService, UserService userService) {
        this.objectMapper = objectMapper;
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @KafkaListener(topics = "tweets")
    public void processMessage(String content) {
        String userJSON = null, tweetJSON = null;
        System.out.println(content);
        try {
            userJSON = objectMapper.readTree(content).get("user").toString();
            tweetJSON = objectMapper.readTree(content).get("tweet").toString();

            Tweet tweet = objectMapper.readValue(tweetJSON, Tweet.class);
            User user = objectMapper.readValue(userJSON, User.class);

            userService.save(user);
            tweetService.save(tweet);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
