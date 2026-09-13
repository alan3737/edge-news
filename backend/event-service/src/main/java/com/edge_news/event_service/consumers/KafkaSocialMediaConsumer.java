package com.edge_news.event_service.consumers;

import org.springframework.stereotype.Component;

import com.edge_news.event_service.services.EventService;
import com.edge_news.event_service.services.EventPriceService;
import com.edge_news.event_service.services.MediaAttachmentService;
import com.edge_news.event_service.services.SocialMediaService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import com.edge_news.event_service.messages.SocialMediaEventMessage;
import com.edge_news.event_service.entities.Event;
import com.edge_news.event_service.entities.SocialMedia;
import com.edge_news.event_service.entities.MediaAttachment;
import org.springframework.web.client.RestClient;
import java.util.List;
import com.edge_news.event_service.entities.EventPrice;

record PriceResponse(
    Long priceId,
    String ticker,
    double price,
    OffsetDateTime time
) {}

@Component
public class KafkaSocialMediaConsumer {

  private final EventService eventService;
  private final SocialMediaService socialMediaService;
  private final MediaAttachmentService mediaAttachmentService;
  private final EventPriceService eventPriceService;
  private final RestClient restClient;

  public KafkaSocialMediaConsumer(EventService eventService, SocialMediaService socialMediaService, MediaAttachmentService mediaAttachmentService, EventPriceService eventPriceService, RestClient restClient) {
    this.eventService = eventService;
    this.socialMediaService = socialMediaService;
    this.mediaAttachmentService = mediaAttachmentService;
    this.eventPriceService = eventPriceService;
    this.restClient = restClient;
  }

  @KafkaListener(topics = "news.raw.social")
  public void consumeSocialMedia(SocialMediaEventMessage socialMediaEventMessage) {
    // Handle the consumed social media event
    OffsetDateTime roundedTimestamp = socialMediaEventMessage.getTime().atOffset(ZoneOffset.UTC).withSecond(0).withNano(0);
    Event event = new Event(socialMediaEventMessage.getSource(), roundedTimestamp, null, socialMediaEventMessage.getSourceType());
    eventService.saveEvent(event);

    SocialMedia socialMedia = new SocialMedia(event, socialMediaEventMessage.getEventMessageId(), socialMediaEventMessage.getUrl(), socialMediaEventMessage.getContent(), socialMediaEventMessage.getName(), socialMediaEventMessage.getCardTitle(), socialMediaEventMessage.getCardUrl());
    socialMediaService.saveSocialMedia(socialMedia);

    for(String mediaAttachmentUrl : socialMediaEventMessage.getMediaAttachments()) {
      MediaAttachment mediaAttachment = new MediaAttachment(mediaAttachmentUrl, socialMedia);
      mediaAttachmentService.saveMediaAttachment(mediaAttachment);
    }

    List<PriceResponse> prices = restClient.get()
      .uri("http://price-service:4999/prices/latest")
      .retrieve()
      .body(new ParameterizedTypeReference<List<PriceResponse>>() {});

    for(PriceResponse priceResponse : prices) {
      if(priceResponse.time().equals(roundedTimestamp)) {
        eventPriceService.saveEventPrice(new EventPrice(event, priceResponse.price(), priceResponse.price(), priceResponse.ticker()));
      }
      else{
        eventPriceService.saveEventPrice(new EventPrice(event, 0, 0, priceResponse.ticker()));
      }
    }
    System.out.println("Received social media event from Kafka: " + socialMediaEventMessage);
  }
}
