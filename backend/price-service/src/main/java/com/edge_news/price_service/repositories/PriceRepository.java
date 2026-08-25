package com.edge_news.price_service.repositories;

import com.edge_news.price_service.entities.Price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
  @Query("""
          SELECT p
          FROM Price p
          WHERE p.timestamp = (
              SELECT MAX(p2.timestamp)
              FROM Price p2
              WHERE p2.ticker = p.ticker
          )
      """)
  List<Price> findLatestForEveryTicker();
}
