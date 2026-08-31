package com.edge_news.event_polling_service.message;

import java.time.Instant;
public class MeetingEventMessage extends EventMessage {

    MeetingEventMessage(String eventMessageId, String title, String source, Instant time, String sourceType) {
        super(eventMessageId, source, time, sourceType);
    }
    // Getters and setters
}