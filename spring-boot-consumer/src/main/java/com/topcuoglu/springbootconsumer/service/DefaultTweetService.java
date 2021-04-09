package com.topcuoglu.springbootconsumer.service;

import com.topcuoglu.springbootconsumer.dao.TweetRepository;
import com.topcuoglu.springbootconsumer.entity.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultTweetService implements TweetService {

    TweetRepository tweetRepository;

    @Autowired
    public DefaultTweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet findById(long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);

        if (optionalTweet.isPresent())
            return optionalTweet.get();
        else
            throw new RuntimeException("Tweet with id:" + id + " is not found!");
    }

    @Override
    public void save(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    @Override
    public void deleteById(long id) {
        tweetRepository.deleteById(id);
    }
}
