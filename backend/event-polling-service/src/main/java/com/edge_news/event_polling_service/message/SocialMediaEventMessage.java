package com.edge_news.event_polling_service.message;
import java.time.Instant;
public class SocialMediaEventMessage extends EventMessage {
    private String name;
    private String url;

    public SocialMediaEventMessage(String eventMessageId, String title, String source, Instant time, String sourceType, String name, String url) {
        super(eventMessageId, title, source, time, sourceType);
        this.name = name;
        this.url = url;
    }

}