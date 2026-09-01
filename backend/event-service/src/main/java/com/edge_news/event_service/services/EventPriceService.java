package com.edge_news.event_service.services;

import org.springframework.stereotype.Service;
import com.edge_news.event_service.repositories.EventPriceRepository;
import com.edge_news.event_service.entities.EventPrice;
import java.util.List;

@Service
public class EventPriceService {
  
  private final EventPriceRepository eventPriceRepository;

  public EventPriceService(EventPriceRepository eventPriceRepository) {
    this.eventPriceRepository = eventPriceRepository;
  }

  public List<EventPrice> getEventPricesByEventIds(List<Long> eventIds) {
    return eventPriceRepository.findByEventEventIdIn(eventIds);
  }

  public void saveEventPrices(List<EventPrice> eventPrices) {
    eventPriceRepository.saveAll(eventPrices);
  }
  
}
