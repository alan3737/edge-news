
package com.edge_news.event_polling_service.source;

import com.edge_news.event_polling_service.message.EconomicNewsEventMessage;
import com.edge_news.event_polling_service.message.EventMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

record FredReleaseDateResponse(List<ReleaseDate> release_dates) {}

record ReleaseDate(int release_id, String release_name, String release_last_updated, String date) {}

record ObservationResponse(List<Observation> observations) {}

record Observation(String realtime_start, String realtime_end, String date, String value) {}

record FredSeriesConfig(String series_id, String release_id, String release_name) {}

@Component
public class FredSource implements NewsSource {


    private final RestClient restClient;

    @Value("${fred.api.key}")
    private String fredApiKey;

    private static final Map<String, FredSeriesConfig> RELEASE_TO_SERIES = Map.of(
        "Consumer Price Index",                              new FredSeriesConfig("CPIAUCSL", "10", "Consumer Price Index"),
        "Employment Situation",                               new FredSeriesConfig("UNRATE", "50", "Unemployment Rate"),
        "Gross Domestic Product",                             new FredSeriesConfig("GDPC1", "53", "Real GDP"),
        "Personal Income and Outlays",                        new FredSeriesConfig("PCEPI", "54", "PCE Price Index (Inflation)"),
        "New Residential Construction",                       new FredSeriesConfig("HOUST", "27", "Housing Starts"),
        "CBOE Market Statistics",                             new FredSeriesConfig("VIXCLS", "200", "VIX (Volatility Index)"),
        "Advance Monthly Sales for Retail and Food Services", new FredSeriesConfig("RSAFS", "9", "Retail Sales"),
        "Unemployment Insurance Weekly Claims Report",        new FredSeriesConfig("ICSA", "180", "Initial Jobless Claims")
    );

    public FredSource(RestClient restClient) {
        this.restClient = restClient;
    }
    private final Map<String, LocalDate> lastProcessedDates = new ConcurrentHashMap<>();
    @Override
    public List<EventMessage> fetchAndNormalize() throws IOException {
        System.out.println("Fetching data from FRED API...");
        List<ReleaseDate> releases = getReleaseDates().release_dates();
        if (releases.isEmpty()) {
            return new ArrayList<>();
        }

        return releases.stream()
            .map(release -> {
                try {
                    FredSeriesConfig seriesConfig = RELEASE_TO_SERIES.get(release.release_name());
                    // Check if the series configuration is available for this release
                    if (seriesConfig == null) {
                        return null;
                    }
                    // Check if this release has already been processed
                    if (lastProcessedDates.containsKey(seriesConfig.series_id()) && lastProcessedDates.get(seriesConfig.series_id()).isEqual(LocalDate.parse(release.date()))) {
                        return null;
                    }
                    Observation releaseData = getReleaseData(seriesConfig.series_id());
                    // Check if the release data is available
                    if (releaseData == null) {
                        return null;
                    }
                    lastProcessedDates.put(seriesConfig.series_id(), LocalDate.parse(release.date()));
                    EventMessage event = new EconomicNewsEventMessage(
                        seriesConfig.series_id() + "-" + release.date(),
                        seriesConfig.series_id(),
                        "FRED",
                        LocalDateTime.parse(release.date() + "T00:00:00").toInstant(ZoneOffset.UTC),
                        "EconomicNews",
                        releaseData.value()
                    );
                    return event;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .filter(Objects::nonNull)
            .toList();
    }

    private FredReleaseDateResponse getReleaseDates() throws IOException {
        String url = "https://api.stlouisfed.org/fred/releases/dates?api_key=%s&file_type=json&sort_order=asc&include_release_dates_with_no_data=true&realtime_start=%s&realtime_end=9999-12-31"
            .formatted(fredApiKey, LocalDate.now());

        FredReleaseDateResponse response = this.restClient.get()
            .uri(url)
            .retrieve()
            .body(FredReleaseDateResponse.class);

        if (response == null) {
            return new FredReleaseDateResponse(new ArrayList<>());
        }

        return new FredReleaseDateResponse(
            response.release_dates().stream()
                .filter(release -> RELEASE_TO_SERIES.containsKey(release.release_name()) && release.date().equals(LocalDate.now().toString()))
                .toList()
        );
        
    }

    private Observation getReleaseData(String series_id) throws IOException {
        String url = "https://api.stlouisfed.org/fred/series/observations?series_id=%s&api_key=%s&file_type=json&sort_order=desc&limit=1".formatted(series_id, fredApiKey);
        ObservationResponse response = this.restClient.get()
            .uri(url)
            .retrieve()
            .body(ObservationResponse.class);
        if (response == null || response.observations().isEmpty()) {
            throw new IllegalStateException("No observations found for series_id: " + series_id);
        }

        return response.observations().get(0);
    }
}