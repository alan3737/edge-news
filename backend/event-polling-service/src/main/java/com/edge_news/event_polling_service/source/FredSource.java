
package com.edge_news.event_polling_service.source;

import com.edge_news.event_polling_service.message.EconomicNewsEventMessage;
import com.edge_news.event_polling_service.message.EventMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestClient;

record FredReleaseDate(int releaseId, String releaseName, LocalDateTime lastUpdated, LocalDate date) {
}


class FredSource implements NewsSource {



    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<String> requiredReleaseNames = List.of(
        "Consumer Price Index for All Urban Consumers: All Items in U.S. City Average",
        "Producer Price Index by Commodity: Finished Goods",
        "Gross Domestic Product",
        "Personal Income and Outlays",
        "Employment Situation",
        "Retail Sales",
        "Industrial Production and Capacity Utilization",
        "Housing Starts and Building Permits"
    );
    public FredSource(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<EventMessage> fetchAndNormalize() throws IOException {
        List<FredReleaseDate> releases = getReleaseDates();
        if (releases.isEmpty()) {
            return new ArrayList<>();
        }

        return releases.stream()
            .map(release -> new EconomicNewsEventMessage(
                "fred-release-%d-%s".formatted(release.releaseId(), release.date()),
                release.releaseName(),
                "FRED",
                release.date().atStartOfDay(ZoneOffset.UTC).toInstant(),
                "EconomicNews",
                "https://fred.stlouisfed.org/release?rid=%d".formatted(release.releaseId())
            ))
            .map(event -> (EventMessage) event)
            .toList();
    }

    private List<FredReleaseDate> getReleaseDates() throws IOException {
        String url = "https://api.stlouisfed.org/fred/releases/dates?api_key=d7ff24e1190ccacfcbbbc226e1046149&file_type=json&sort_order=asc&include_release_dates_with_no_data=true&realtime_start=%s&realtime_end=9999-12-31"
            .formatted(LocalDate.now());

        String response = this.restClient.get()
            .uri(url)
            .retrieve()
            .body(String.class);

        if (response == null || response.isBlank()) {
            return new ArrayList<>();
        }

        return parseReleaseDates(response).stream()
            .filter(release -> requiredReleaseNames.contains(release.releaseName()))
            .toList();
    }

    private List<FredReleaseDate> parseReleaseDates(String response) throws IOException {
        JsonNode releaseDates = objectMapper.readTree(response).path("release_dates");
        List<FredReleaseDate> releases = new ArrayList<>();
        for (JsonNode releaseDate : releaseDates) {
            if (releaseDate.hasNonNull("release_id")
                && releaseDate.hasNonNull("release_name")
                && releaseDate.hasNonNull("date")) {
                releases.add(new FredReleaseDate(
                    releaseDate.get("release_id").asInt(),
                    releaseDate.get("release_name").asText(),
                    LocalDate.parse(releaseDate.get("date").asText())
                ));
            }
        }
        return releases;
    }
}