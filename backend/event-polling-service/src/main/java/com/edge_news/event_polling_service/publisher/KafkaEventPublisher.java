package com.edge_news.event_polling_service.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.edge_news.event_polling_service.message.EventMessage;

@Component
public class KafkaEventPublisher implements Publisher {
    /**
     * Implementation of KafkaEventPublisher
     */
    private final KafkaTemplate<String, EventMessage> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, EventMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(EventMessage message) {
        String topic = resolveTopic(message.getSourceType());
        kafkaTemplate.send(topic, message.getEventMessageId(), message);
    }

    private String resolveTopic(String sourceType) {
        return switch (sourceType) {
            case "EconomicNews" -> "news.raw.economic";
            case "Meeting" -> "news.raw.meeting";
            case "SocialMedia" -> "news.raw.social";
            default -> throw new IllegalArgumentException("Unknown sourceType: " + sourceType);
        };
    }
}
