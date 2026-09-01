package com.edge_news.event_polling_service.message;

import java.time.Instant;
public class EconomicNewsEventMessage extends EventMessage {
    private String content;

    public EconomicNewsEventMessage(String eventMessageId, String title, String source, Instant time, String sourceType, String content) {
        super(eventMessageId, source, time, sourceType);
        this.content = content;
    }
    


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }
}


