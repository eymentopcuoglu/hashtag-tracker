package com.topcuoglu.springbootconsumer.controller;

import com.topcuoglu.springbootconsumer.entity.Tweet;
import com.topcuoglu.springbootconsumer.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.findAll();
    }

    @GetMapping("/{id}")
    public Tweet getTweet(@PathVariable long id) {
        return tweetService.findById(id);
    }

    @PostMapping
    public Tweet saveTweet(@RequestBody Tweet tweet) {
        tweet.setId(0);
        tweetService.save(tweet);
        return tweet;
    }

    @PutMapping("/{id}")
    public Tweet updateTweet(@RequestBody Tweet tweet, @PathVariable long id) {
        tweet.setId(id);
        tweetService.save(tweet);
        return tweet;
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable long id) {
        tweetService.deleteById(id);
    }
}
