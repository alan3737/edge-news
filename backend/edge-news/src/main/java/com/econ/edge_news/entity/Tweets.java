package com.econ.edge_news.entity;
import java.time.Instant;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tweets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tweet_id;

    String tweet_imageURL;
    String tweet_content;
    @Column(nullable = false)
    Instant tweet_date;
}
