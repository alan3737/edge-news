package com.edge_news.event_service.services;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edge_news.event_service.entities.Event;
import com.edge_news.event_service.repositories.EventRepository;

@Service
public class EventService {
  private final EventRepository eventRepository;

  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public List<Event> getEventsWithinLast30Minutes() {
    OffsetDateTime cutoff = OffsetDateTime.now(ZoneOffset.UTC)
    .withSecond(0)
    .withNano(0)
    .minusMinutes(30);

    return eventRepository.findEventsWithinLast30Minutes(cutoff);
  }
}
