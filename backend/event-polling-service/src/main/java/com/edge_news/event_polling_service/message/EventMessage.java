package com.edge_news.event_polling_service.message;

import java.time.Instant;

public class EventMessage {
    private String eventMessageId;       // UUID, generated at ingestion
    private String title;         // short human-readable headline
    private String source;        // e.g. "FRED", "Twitter", "Fed"
    private Instant time;         // when the underlying event happened
    private String sourceType;    // "EconomicNews" | "Meeting" | "SocialMedia"

    public EventMessage(String eventMessageId, String title, String source, Instant time, String sourceType) {
        this.eventMessageId = eventMessageId;
        this.title = title;
        this.source = source;
        this.time = time;
        this.sourceType = sourceType;
    }

    public String getEventMessageId() {
        return eventMessageId;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public Instant getTime() {
        return time;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setEventMessageId(String eventMessageId) {
        this.eventMessageId = eventMessageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
} 