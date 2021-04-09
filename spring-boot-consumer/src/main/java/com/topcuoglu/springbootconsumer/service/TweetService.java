package com.topcuoglu.springbootconsumer.service;

import com.topcuoglu.springbootconsumer.entity.Tweet;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TweetService {

    public List<Tweet> findAll();

    public Tweet findById(long id);

    public void save(Tweet tweet);

    public void deleteById(long id);

}
