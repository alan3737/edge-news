package com.econ.edge_news.entity;
import java.time.Instant;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tweetId;

    String tweetImageURL;
    String tweetContent;
    Instant tweetDate;

    public Tweet(){

    }

    public Tweet(String tweetImageURL, String tweetContent, Instant tweetDate){
        this.tweetImageURL = tweetImageURL;
        this.tweetContent = tweetContent;
        this.tweetDate = tweetDate;
    }
}
