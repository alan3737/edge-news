package com.edge_news.event_service.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.edge_news.event_service.services.EventService;
import com.edge_news.event_service.messages.PriceMessage;
import com.edge_news.event_service.entities.Event;
import com.edge_news.event_service.entities.EventPrice;
import com.edge_news.event_service.services.EventPriceService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class KafkaPriceConsumer {

  private final EventService eventService;
  private final EventPriceService eventPriceService;

  public KafkaPriceConsumer(EventService eventService, EventPriceService eventPriceService) {
    this.eventService = eventService;
    this.eventPriceService = eventPriceService;
  }

  @KafkaListener(topics = "prices")
  public void consumePrice(List<PriceMessage> prices) {
    System.out.println("Received prices from Kafka:" + prices);

    List<Event> events = eventService.getEventsWithinLast30Minutes();
    if(events.isEmpty()) {
      return;
    }
    List<Long> eventIds = events.stream()
    .map(Event::getEventId)
    .toList();
    List<EventPrice> eventPrices = eventPriceService.getEventPricesByEventIds(eventIds);
    Map<String, PriceMessage> priceMap = new HashMap<>();
    for(PriceMessage price : prices) {
      priceMap.put(price.getTicker(), price);
    }

    for(EventPrice eventPrice : eventPrices) {
      PriceMessage price = priceMap.get(eventPrice.getTicker());
      if(price != null) {
        eventPrice.setCurrentPrice(price.getPrice());
        if(eventPrice.getStartPrice() == 0){
          eventPrice.setStartPrice(price.getPrice());
        }
      }
    }
    eventPriceService.saveEventPrices(eventPrices);
  }
}
