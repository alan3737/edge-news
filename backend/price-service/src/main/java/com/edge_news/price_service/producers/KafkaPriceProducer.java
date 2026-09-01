package com.edge_news.price_service.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.edge_news.price_service.messages.PriceMessage;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
class KafkaTopicConfig {

    @Bean
    public NewTopic pricesTopic() {
        return TopicBuilder.name("prices")
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}

@Component
public class KafkaPriceProducer{
    // Implementation of KafkaPriceProducer
    private final KafkaTemplate<String, List<PriceMessage>> kafkaTemplate;

    public KafkaPriceProducer(KafkaTemplate<String, List<PriceMessage>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(List<PriceMessage> prices) {
        kafkaTemplate.send("prices", "latest_prices", prices);
    }
}
