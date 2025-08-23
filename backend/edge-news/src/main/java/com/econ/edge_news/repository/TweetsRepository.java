package com.econ.edge_news.repository;

import com.econ.edge_news.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetsRepository extends JpaRepository<Tweets, Long>{
    
}
