package com.edge_news.price_service.publishers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.edge_news.price_service.entities.Price;
import java.util.List;


@Configuration
class KafkaTopicConfig {

    @Bean
    public NewTopic pricesTopic() {
        return TopicBuilder.name("prices")
            .partitions(3)
            .replicas(1)
            .build();
    }
}

@Component
public class KafkaPricePublisher{
    // Implementation of KafkaPricePublisher
    private final KafkaTemplate<String, List<Price>> kafkaTemplate;

    public KafkaPricePublisher(KafkaTemplate<String, List<Price>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(List<Price> prices) {
        kafkaTemplate.send("prices", "latest_prices", prices);
    }
}
