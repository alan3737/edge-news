package com.edge_news.event_polling_service.message;

import java.time.Instant;
import java.util.List;

public class SocialMediaEventMessage extends EventMessage {

    private String name;        // poster's display name (was already there)
    private String url;         // link to the original post (was already there)
    private String content;     // plain-text post content
    private String cardTitle;   // optional link-preview title
    private String cardUrl;     // optional link-preview URL
    private List<String> media_attachments; // image/video URLs, if any

    public SocialMediaEventMessage(
        String eventMessageId,
        String source,
        Instant time,
        String sourceType,
        String name,
        String url,
        String content,
        String cardTitle,
        String cardUrl,
        List<String> media_attachments
    ) {
        super(eventMessageId, source, time, sourceType);
        this.name = name;
        this.url = url;
        this.content = content;
        this.cardTitle = cardTitle;
        this.cardUrl = cardUrl;
        this.media_attachments = media_attachments;
    }

    public String getName() { return name; }
    public String getUrl() { return url; }
    public String getContent() { return content; }
    public String getCardTitle() { return cardTitle; }
    public String getCardUrl() { return cardUrl; }
    public List<String> getMediaAttachments() { return media_attachments; }
}