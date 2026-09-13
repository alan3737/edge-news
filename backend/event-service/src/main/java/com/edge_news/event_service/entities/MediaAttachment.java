package com.edge_news.event_service.entities;

import jakarta.persistence.*;

@Entity
public class MediaAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaAttachmentId;

    private String url;

    @ManyToOne
    @JoinColumn(name = "socialMediaId", nullable = false)
    private SocialMedia socialMedia;

    public MediaAttachment() {}

    public MediaAttachment(String url, SocialMedia socialMedia) {
        this.url = url;
        this.socialMedia = socialMedia;
    }

    public Long getMediaAttachmentId() {
        return mediaAttachmentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }
}
