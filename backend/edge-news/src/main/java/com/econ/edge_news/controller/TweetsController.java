package com.econ.edge_news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.econ.edge_news.repository.TweetsRepository;
import com.econ.edge_news.entity.Tweets;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

    @Autowired
    private TweetsRepository repository;

    @GetMapping
    public List<Tweets> getAllTweets(){
        return repository.findAll();
    }

    @PostMapping
    public void addTweet(@RequestBody Tweets tweet){
        repository.save(tweet);
    }
}
