package com.edge_news.event_polling_service.config;

import java.beans.BeanProperty;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {

    @Bean
    public NewTopic economicNewsTopic() {
        return TopicBuilder.name("news.raw.economic")
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic socialMediaNewsTopic() {
        return TopicBuilder.name("news.raw.social")
            .partitions(3)
            .replicas(1)
            .build();
    }
}
