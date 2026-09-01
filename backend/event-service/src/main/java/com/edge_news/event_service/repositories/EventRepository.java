package com.edge_news.event_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edge_news.event_service.entities.Event;

import java.time.OffsetDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>{
  @Query("""
    SELECT e
    FROM Event e
    WHERE e.pubDateTime >= :cutoff
  """)
  List<Event> findEventsWithinLast30Minutes(OffsetDateTime cutoff);
}
