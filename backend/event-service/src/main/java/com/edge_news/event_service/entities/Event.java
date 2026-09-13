package com.edge_news.event_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.OffsetDateTime;

@Entity
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eventId;

  @OneToOne(mappedBy = "event")
  private EconomicNews economicNews;

  @OneToOne(mappedBy = "event")
  private Meeting meeting;

  @OneToOne(mappedBy = "event")
  private SocialMedia socialMedia;

  private String source;
  private OffsetDateTime pubDateTime;
  @Column(columnDefinition = "TEXT")
  private String aiSummary;
  private String sourceType;

  public Event() {}   

  public Event(String source, OffsetDateTime pubDateTime, String aiSummary, String sourceType) {
    this.source = source;
    this.pubDateTime = pubDateTime;
    this.aiSummary = aiSummary;
    this.sourceType = sourceType;
  }

  public Long getEventId() {
    return eventId;
  }

  public EconomicNews getEconomicNews() {
    return economicNews;
  }

  public void setEconomicNews(EconomicNews economicNews) {
    this.economicNews = economicNews;
  }

  public Meeting getMeeting() {
    return meeting;
  }

  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }

  public SocialMedia getSocialMedia() {
    return socialMedia;
  }

  public void setSocialMedia(SocialMedia socialMedia) {
    this.socialMedia = socialMedia;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }             

  public OffsetDateTime getPubDateTime() {
    return pubDateTime;
  }

  public void setPubDateTime(OffsetDateTime pubDateTime) {
    this.pubDateTime = pubDateTime;
  }

  public String getAiSummary() {
    return aiSummary;
  }

  public void setAiSummary(String aiSummary) {
    this.aiSummary = aiSummary;
  }

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }
}

