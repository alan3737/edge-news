package com.econ.edge_news.repository;

import com.econ.edge_news.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long>{
    
}
