
package com.edge_news.event_polling_service.source;

import com.fasterxml.jackson.core.type.TypeReference;
import com.edge_news.event_polling_service.events.EconomicNewsEventMessage;
import com.edge_news.event_polling_service.events.EventMessage;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.client.RestClient;

class FredRelease {
    private String id;
    private String release_id;
    private String name;
    private String press_release_url;
    private String press_release_date;
    private String press_release_time;
    private String realtime_start;
    private String realtime_end;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelease_id() {
        return release_id;
    }

    public void setRelease_id(String release_id) {
        this.release_id = release_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPress_release_url() {
        return press_release_url;
    }

    public void setPress_release_url(String press_release_url) {
        this.press_release_url = press_release_url;
    }

    public String getPress_release_date() {
        return press_release_date;
    }

    public void setPress_release_date(String press_release_date) {
        this.press_release_date = press_release_date;
    }

    public String getPress_release_time() {
        return press_release_time;
    }

    public void setPress_release_time(String press_release_time) {
        this.press_release_time = press_release_time;
    }

    public String getRealtime_start() {
        return realtime_start;
    }

    public void setRealtime_start(String realtime_start) {
        this.realtime_start = realtime_start;
    }

    public String getRealtime_end() {
        return realtime_end;
    }

    public void setRealtime_end(String realtime_end) {
        this.realtime_end = realtime_end;
    }
}


class FredSource implements NewsSource {



    private final RestClient restClient;

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
    private final List<String> requiredReleaseIds = List.of(
        "CPIAUCSL",
        "PPIACO",
        "GDP",
        "PI",
        "PAYEMS",
        "RSXFS",
        "INDPRO",
        "HOUST"
    );

    public FredSource(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<EventMessage> fetchAndNormalize() throws IOException {
        List<FredRelease> releases = getReleasedDays();
        if (releases.isEmpty()) {
            return new ArrayList<>();
        }

        return fetchFredReleases(releases).stream()
            .map(fredRelease -> {
                String eventMessageId = fredRelease.getId();
                String title = fredRelease.getName();
                String source = "FRED";
                Instant time = Instant.parse(fredRelease.getPress_release_date() + "T" + fredRelease.getPress_release_time() + "Z");
                String sourceType = "EconomicNews";
                String content = fredRelease.getPress_release_url();

                return new EconomicNewsEventMessage(eventMessageId, title, source, time, sourceType, content);
            })
            .collect(Collectors.toList());
    }

    private List<FredRelease> getReleasedDays() throws IOException {
        String url = "https://api.stlouisfed.org/fred/releases/dates?api_key=d7ff24e1190ccacfcbbbc226e1046149&file_type=json&sort_order=asc&include_release_dates_with_no_data=true&realtime_start=%s&realtime_end=9999-12-31"
            .formatted(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        String response = this.restClient.get()
            .uri(url)
            .retrieve()
            .body(String.class);

        if (response == null || response.isBlank()) {
            return new ArrayList<>();
        }

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        var jsonNode = mapper.readTree(response);
        var releasesNode = jsonNode.get("releases");
        if (releasesNode == null || releasesNode.isNull()) {
            return new ArrayList<>();
        }

        List<FredRelease> fetchedReleases = mapper.convertValue(releasesNode, new TypeReference<List<FredRelease>>() {});
        return fetchedReleases.stream()
            .filter(release -> requiredReleaseIds.contains(release.id) || requiredReleaseIds.contains(release.release_id))
            .collect(Collectors.toList());
    }

    private List<FredRelease> fetchFredReleases(List<FredRelease> releases) {
        return releases;
    }
}