package com.edge_news.event_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edge_news.event_service.entities.EventPrice;
import java.util.List;

public interface EventPriceRepository extends JpaRepository<EventPrice, Long> {
  List<EventPrice> findByEventEventIdIn(List<Long> eventIds);
  
}