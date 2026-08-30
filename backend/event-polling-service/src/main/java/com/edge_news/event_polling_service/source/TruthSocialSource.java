package com.edge_news.event_polling_service.source;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.edge_news.event_polling_service.message.EventMessage;


@Component
public class TruthSocialSource implements NewsSource {

    private final RestClient restClient;

    public TruthSocialSource(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<EventMessage> fetchAndNormalize() throws IOException {
        System.out.println("Fetching events from TruthSocial source...");
        fetchEvents();
        return List.of();
    }
    
    private void fetchEvents() throws IOException {
        String url = "https://truthsocial.com/api/v1/accounts/107780257626128497/statuses?limit=1";
        String response = restClient.get()
            .uri(url)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
            .retrieve()
            .body(String.class);
        System.out.println(response);
    }

}