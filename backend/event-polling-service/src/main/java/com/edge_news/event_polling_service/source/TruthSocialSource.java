package com.edge_news.event_polling_service.source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.edge_news.event_polling_service.message.EventMessage;
import com.edge_news.event_polling_service.message.SocialMediaEventMessage;

record Card(
    String url,
    String title,
    String description
) {}
record Account(
    String id,
    String username,
    String display_name
) {}
record MediaAttachment(
    String id,
    String type,
    String url,
    String preview_url
) {}
record TruthSocialStatus(
    String id,
    String created_at,
    String content,
    String url,
    Account account,
    Card card,
    List<MediaAttachment> media_attachments
) {}

@Component
public class TruthSocialSource implements NewsSource {

    private final RestClient restClient;

    public TruthSocialSource(RestClient restClient) {
        this.restClient = restClient;
    }
    private volatile LocalDate trackedDate = LocalDate.now(); 
    private final Set<String> processedStatusIds = ConcurrentHashMap.newKeySet();
    @Override
    public List<EventMessage> fetchAndNormalize() throws IOException {
        System.out.println("Fetching events from TruthSocial source...");
        LocalDate today = LocalDate.now();
        if (trackedDate.isBefore(today)) {
            processedStatusIds.clear();
            trackedDate = today;
        }
        List<TruthSocialStatus> statuses = fetchStatuses();
        List<EventMessage> events = new ArrayList<>();
        System.out.println("Fetched " + statuses.size() + " statuses from TruthSocial.");
        for (TruthSocialStatus status : statuses) {
            Instant postedAt = Instant.parse(status.created_at());
            LocalDate postedDate = postedAt.atZone(ZoneOffset.UTC).toLocalDate();

            if (!postedDate.equals(today) || processedStatusIds.contains(status.id())) {
                System.out.println("Skipping status: " + status.id() + " (date mismatch or already processed)");
                continue;
            }
            String plainText = Jsoup.parse(status.content()).text();
            List<String> mediaUrls = status.media_attachments() == null
                ? List.of()
                : status.media_attachments().stream().map(MediaAttachment::url).toList();
            if (plainText.isBlank() && status.card() == null && mediaUrls.isEmpty()) {
                System.out.println("Skipping status: " + status.id() + " (no content, card, or media)");
                processedStatusIds.add(status.id());
                continue;
            }

            System.out.println("Processing status: " + status.id());
            events.add(new SocialMediaEventMessage(
                status.id(),                     // eventMessageId
                "TruthSocial",                   // source
                postedAt,                        // time
                "SocialMedia",                   // sourceType
                status.account().display_name(), // name
                status.url(),                    // url
                plainText,                       // content
                status.card() != null ? status.card().title() : null, // cardTitle
                status.card() != null ? status.card().url() : null,   // cardUrl
                mediaUrls                       // media_attachments
            ));
            processedStatusIds.add(status.id());
        }    
        System.out.println("Normalized " + events.size() + " events from TruthSocial.");
            
        return events;
    }
    
    private List<TruthSocialStatus> fetchStatuses() throws IOException {
        String url = "https://truthsocial.com/api/v1/accounts/107780257626128497/statuses?limit=1";
        TruthSocialStatus[] statuses = restClient.get()
            .uri(url)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
            .retrieve()
            .body(TruthSocialStatus[].class);
        return Arrays.asList(statuses);
    }

}