package com.edge_news.event_polling_service;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.client.RestClient;

import com.edge_news.event_polling_service.message.EconomicNewsEventMessage;
import com.edge_news.event_polling_service.message.EventMessage;
import com.edge_news.event_polling_service.source.FredSource;
import com.edge_news.event_polling_service.publisher.Publisher;
@SpringBootApplication
public class EventPollingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventPollingServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner testKafkaOnStartup(Publisher publisher) {
        return args -> {
            EventMessage testMessage = new EconomicNewsEventMessage(
                "GDPC1-2026-01-01",
                "Test CPI Event",
                "FRED",
                Instant.now(),
                "EconomicNews",
                "312.332"
            );

            publisher.publish(testMessage);
            System.out.println("Sent test message: " + testMessage);
        };
    }
}
