package com.econ.edge_news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.econ.edge_news.repository.TweetRepository;
import com.econ.edge_news.entity.Tweet;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetRepository repository;

    @GetMapping
    public List<Tweet> getAllTweets(){
        return repository.findAll();
    }

    @PostMapping
    public void addTweet(@RequestBody Tweet tweet){
        repository.save(tweet);
    }
}
