package com.edge_news.price_service.repositories;

import com.edge_news.price_service.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PriceRepository extends JpaRepository<Price, Long>{
  
}
