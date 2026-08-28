package com.edge_news.event_polling_service.polling;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import com.edge_news.event_polling_service.message.EventMessage;
import com.edge_news.event_polling_service.source.NewsSource;
import com.edge_news.event_polling_service.publisher.Publisher;

@Component
public class PollingService {
    private final List<NewsSource> sources;
    private final Publisher publisher;

    public PollingService(List<NewsSource> sources, Publisher publisher) {
        this.sources = sources;
        this.publisher = publisher;
    }

    @Scheduled(fixedRate = 60000) // every 60,000 ms = 1 minute
    public void pollAllSources() {
        for (NewsSource source : sources) {
            try {
                System.out.println("Polling source: " + source.getClass().getSimpleName());
                List<EventMessage> events = source.fetchAndNormalize();
                System.out.println("Fetched " + events.size() + " events from source: " + source.getClass().getSimpleName());
                events.forEach(publisher::publish);
            } catch (IOException e) {
                // log and continue — one source failing shouldn't stop the others
                System.err.println("Failed to poll source: " + source.getClass().getSimpleName());
                e.printStackTrace();
            }
        }
    }
}
